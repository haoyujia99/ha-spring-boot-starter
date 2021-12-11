package cn.developer.howie.thread;

import cn.developer.howie.model.ao.HeartBeatMessage;
import cn.developer.howie.model.constant.HighAvailableConstant;
import cn.developer.howie.model.property.HighAvailableProperties;
import cn.developer.howie.util.GsonUtils;
import cn.developer.howie.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * cn.developer.howie.thread.HeartBeatSendThread.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 10:43 AM
 */
public class HeartBeatSendThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatSendThread.class);

    private final HighAvailableProperties highAvailableProperties;

    public HeartBeatSendThread(HighAvailableProperties highAvailableProperties) {
        this.highAvailableProperties = highAvailableProperties;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        logger.info("### Launching heartbeat send thread... ###");

        String localHostAddress = IpUtils.getByName(highAvailableProperties.getNetworkInterface());
        String targetHostAddress = getTargetHost(localHostAddress);
        Integer port = highAvailableProperties.getHeartbeatPort();
        send(localHostAddress, new InetSocketAddress(targetHostAddress, port));
    }

    private String getTargetHost(String localHostAddress) {

        for (String hostAddress : highAvailableProperties.getHostAddresses()) {
            if (!hostAddress.equals(localHostAddress)) {
                return hostAddress;
            }
        }
        return localHostAddress;
    }

    private void send(String localHostAddress, InetSocketAddress inetSocketAddress) {

        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            while (true) {
                String message = GsonUtils.toJson(new HeartBeatMessage(localHostAddress, LocalTime.now(), HighAvailableConstant.NODE_STATUS_ENUM.get()));
                if (logger.isDebugEnabled()) {
                    logger.debug("### Send: {} ###", message);
                }
                DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(StandardCharsets.UTF_8), message.length(), inetSocketAddress);
                datagramSocket.send(datagramPacket);
                TimeUnit.SECONDS.sleep(highAvailableProperties.getHeartbeatInterval());
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

}