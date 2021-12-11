package cn.developer.howie.thread;

import cn.developer.howie.model.ao.HeartBeatMessage;
import cn.developer.howie.model.constant.HighAvailableConstant;
import cn.developer.howie.model.enums.NodeStatusEnum;
import cn.developer.howie.model.property.HighAvailableProperties;
import cn.developer.howie.service.HighAvailableService;
import cn.developer.howie.service.NodeSelectionStrategy;
import cn.developer.howie.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * cn.developer.howie.thread.HeartBeatMonitorThread.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/4/2021 2:49 PM
 */
public class HeartBeatMonitorThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatMonitorThread.class);

    private final BlockingQueue<String> blockingQueue;
    private final HighAvailableProperties highAvailableProperties;
    private final HighAvailableService highAvailableService;
    private final NodeSelectionStrategy nodeSelectionStrategy;

    public HeartBeatMonitorThread(
            BlockingQueue<String> blockingQueue,
            HighAvailableProperties highAvailableProperties,
            HighAvailableService highAvailableService,
            NodeSelectionStrategy nodeSelectionStrategy) {
        this.blockingQueue = blockingQueue;
        this.highAvailableProperties = highAvailableProperties;
        this.highAvailableService = highAvailableService;
        this.nodeSelectionStrategy = nodeSelectionStrategy;
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
        while (true) {
            try {
                String message = blockingQueue.poll(heartbeatTimeout, TimeUnit.SECONDS);
                if (null == message) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("### Hasn't receive heart beat message over {}s, becoming master node ###", heartbeatTimeout);
                    }
                    if (!NodeStatusEnum.MASTER.equals(HighAvailableConstant.NODE_STATUS_ENUM.get())) {
                        highAvailableService.onMaster();
                    }
                } else {
                    validateMessage(message);
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void validateMessage(String message) {

        HeartBeatMessage heartBeatMessage = GsonUtils.fromJson(message, HeartBeatMessage.class);
        if (NodeStatusEnum.MASTER.equals(HighAvailableConstant.NODE_STATUS_ENUM.get())
                && NodeStatusEnum.MASTER.equals(heartBeatMessage.getNodeStatusEnum())) {
            logger.warn("### Both target and current node have become master node, re-select the master node ###");
            if (nodeSelectionStrategy.canBeMaster(heartBeatMessage)) {
                highAvailableService.onMaster();
            } else {
                highAvailableService.onSlave();
            }
        }
    }

}