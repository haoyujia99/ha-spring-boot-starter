package cn.developer.howie.service;

import cn.developer.howie.model.ao.HeartBeatMessage;

/**
 * cn.developer.howie.service.NodeSelectionStrategy.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 12:37 PM
 */
public interface NodeSelectionStrategy {

    /**
     * choose the master node by customize strategy
     * the default strategy will choose the bigger ip address as master node
     *
     * @param heartBeatMessage received message
     * @return is current service can be master node
     */
    boolean canBeMaster(HeartBeatMessage heartBeatMessage);

}