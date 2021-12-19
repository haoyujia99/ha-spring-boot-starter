package cn.developer.howie.service.impl;

import cn.developer.howie.model.ao.HeartBeatMessage;
import cn.developer.howie.model.constant.HighAvailableConstant;
import cn.developer.howie.model.enums.NodeStatusEnum;
import cn.developer.howie.model.property.HighAvailableProperties;
import cn.developer.howie.service.HighAvailableService;
import cn.developer.howie.service.NodeSelectionStrategy;
import cn.developer.howie.service.ServiceInitializationHandler;
import cn.developer.howie.thread.HeartBeatMonitorThread;
import cn.developer.howie.thread.HeartBeatReceiveThread;
import cn.developer.howie.thread.HeartBeatSendThread;
import cn.developer.howie.util.CommandLineUtils;
import cn.developer.howie.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * cn.developer.howie.service.impl.HighAvailableServiceImpl.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 10:11 AM
 */
public class HighAvailableServiceImpl implements HighAvailableService {

    private static final Logger logger = LoggerFactory.getLogger(HighAvailableServiceImpl.class);

    private final BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

    private final HighAvailableProperties highAvailableProperties;
    private final ServiceInitializationHandler serviceInitializationHandler;
    private final NodeSelectionStrategy nodeSelectionStrategy;

    public HighAvailableServiceImpl(
            HighAvailableProperties highAvailableProperties,
            ServiceInitializationHandler serviceInitializationHandler,
            NodeSelectionStrategy nodeSelectionStrategy) {
        this.highAvailableProperties = highAvailableProperties;
        this.serviceInitializationHandler = serviceInitializationHandler;
        this.nodeSelectionStrategy = nodeSelectionStrategy;
    }

    @PostConstruct
    public void init() {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                3,
                5,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                runnable -> new Thread(runnable, "ha-thread")
        );

        threadPoolExecutor.execute(new HeartBeatReceiveThread(highAvailableProperties, blockingQueue));
        threadPoolExecutor.execute(new HeartBeatSendThread(highAvailableProperties));
        threadPoolExecutor.execute(new HeartBeatMonitorThread(
                blockingQueue,
                highAvailableProperties,
                this,
                nodeSelectionStrategy
        ));
    }

    /**
     * service has started, check current node status
     *
     * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied,
     *                              and the thread is interrupted, either before or during the activity
     */
    @Override
    public void ready() throws InterruptedException {

        String message = blockingQueue.poll(highAvailableProperties.getInitializationTimeWait(), TimeUnit.SECONDS);
        if (null == message) {
            logger.info("### Hasn't receive heart beat message, becoming master node... ###");
            onMaster();
        } else {
            HeartBeatMessage heartBeatMessage = GsonUtils.fromJson(message, HeartBeatMessage.class);
            if (NodeStatusEnum.MASTER.equals(heartBeatMessage.getNodeStatusEnum())) {
                onSlave();
            } else {
                if (nodeSelectionStrategy.canBeMaster(heartBeatMessage)) {
                    onMaster();
                } else {
                    onSlave();
                }
            }
        }
    }

    /**
     * when node becomes master
     */
    @Override
    public void onMaster() {

        HighAvailableConstant.NODE_STATUS_ENUM.set(NodeStatusEnum.MASTER);
        bindVirtualHost();
        refreshArpCache();
        logger.info("### Current node has become the master node ###");

        serviceInitializationHandler.onMaster();
    }

    private void bindVirtualHost() {

        CommandLineUtils.bindVirtualHost(
                highAvailableProperties.getVirtualNetworkInterface(),
                highAvailableProperties.getVirtualHostAddress(),
                highAvailableProperties.getNetmask()
        );
        logger.info("### Bound virtual host ###");
    }

    private void refreshArpCache() {

        CommandLineUtils.refreshArpCache(
                highAvailableProperties.getNetworkInterface(),
                highAvailableProperties.getVirtualHostAddress()
        );
        logger.info("### Refresh Arp Cache ###");
    }

    /**
     * when node becomes slave
     */
    @Override
    public void onSlave() {

        HighAvailableConstant.NODE_STATUS_ENUM.set(NodeStatusEnum.SLAVE);
        logger.info("### Current node has become the slave node ###");

        CommandLineUtils.unbindVirtualHost(highAvailableProperties.getVirtualNetworkInterface(), highAvailableProperties.getVirtualHostAddress());
        logger.info("### Unbound exist virtual host configuration ###");

        serviceInitializationHandler.onSlave();
    }

}