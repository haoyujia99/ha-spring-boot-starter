package cn.developer.howie.thread;

import cn.developer.howie.model.property.HighAvailableProperties;
import cn.developer.howie.util.CommandLineUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * cn.developer.howie.thread.HeartBeatReceiveThread.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 10:14 AM
 */
public class HeartBeatReceiveThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatReceiveThread.class);

    private static final int BUFFER_LENGTH = 1024;

    private final AtomicBoolean interrupt = new AtomicBoolean(false);

    private final HighAvailableProperties highAvailableProperties;
    private final BlockingQueue<String> blockingQueue;

    public HeartBeatReceiveThread(
            HighAvailableProperties highAvailableProperties,
            BlockingQueue<String> blockingQueue) {
        this.highAvailableProperties = highAvailableProperties;
        this.blockingQueue = blockingQueue;
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

        logger.info("### Launching heartbeat receive thread... ###");

        CommandLineUtils.unbindVirtualHost(highAvailableProperties.getVirtualNetworkInterface(), highAvailableProperties.getVirtualHostAddress());
        logger.info("### Unbound exist virtual host configuration ###");

        Integer heartbeatPort = highAvailableProperties.getHeartbeatPort();
        logger.info("### Listening on port: {} ###", heartbeatPort);

        byte[] bytebuffer = new byte[BUFFER_LENGTH];
        DatagramPacket datagramPacket = new DatagramPacket(bytebuffer, bytebuffer.length);

        try (DatagramSocket datagramSocket = new DatagramSocket(heartbeatPort)) {
            while (!interrupt.get()) {
                datagramSocket.receive(datagramPacket);
                String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), StandardCharsets.UTF_8);
                if (logger.isDebugEnabled()) {
                    logger.debug("### receive: {} ###", message);
                }
                blockingQueue.add(message);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * interrupt current thread
     */
    public void interrupt() {

        interrupt.set(true);
        blockingQueue.clear();
        logger.info("### HeartBeatReceiveThread has been interrupted ###");
    }

}