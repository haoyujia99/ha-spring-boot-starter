package cn.developer.howie.thread;

import cn.developer.howie.model.property.HighAvailableProperties;
import cn.developer.howie.service.HighAvailableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * cn.developer.howie.thread.HeartBeatMonitorThread.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/4/2021 2:49 PM
 */
public class HeartBeatMonitorThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatMonitorThread.class);

    private final AtomicBoolean interrupt = new AtomicBoolean(false);

    private final BlockingQueue<String> blockingQueue;
    private final HighAvailableProperties highAvailableProperties;
    private final HighAvailableService highAvailableService;

    public HeartBeatMonitorThread(
            BlockingQueue<String> blockingQueue,
            HighAvailableProperties highAvailableProperties,
            HighAvailableService highAvailableService) {
        this.blockingQueue = blockingQueue;
        this.highAvailableProperties = highAvailableProperties;
        this.highAvailableService = highAvailableService;
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

        logger.info("### Launching heartbeat monitor thread... ###");

        Integer heartbeatTimeout = highAvailableProperties.getHeartbeatTimeout();
        while (!interrupt.get()) {
            try {
                String message = blockingQueue.poll(heartbeatTimeout, TimeUnit.SECONDS);
                if (null == message) {
                    logger.info("### Hasn't receive heart beat message over {}s, becoming master node... ###", heartbeatTimeout);
                    highAvailableService.onMaster();
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * interrupt current thread
     */
    public void interrupt() {

        interrupt.set(true);
        logger.info("### HeartBeatMonitorThread has been interrupted ###");
    }

}