package cn.developer.howie.model.ao;

import cn.developer.howie.model.enums.NodeStatusEnum;

import java.time.LocalTime;

/**
 * cn.developer.howie.model.ao.HeartBeatMessage.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 10:45 AM
 */
public class HeartBeatMessage {

    /**
     * local host address
     */
    private String address;

    /**
     * current time
     */
    private LocalTime localTime;

    /**
     * current node status
     */
    private NodeStatusEnum nodeStatusEnum;

    public HeartBeatMessage(String address, LocalTime localTime, NodeStatusEnum nodeStatusEnum) {
        this.address = address;
        this.localTime = localTime;
        this.nodeStatusEnum = nodeStatusEnum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public NodeStatusEnum getNodeStatusEnum() {
        return nodeStatusEnum;
    }

    public void setNodeStatusEnum(NodeStatusEnum nodeStatusEnum) {
        this.nodeStatusEnum = nodeStatusEnum;
    }

    @Override
    public String toString() {
        return "HeartBeatMessage{" +
                "address='" + address + '\'' +
                ", localTime=" + localTime +
                ", nodeStatusEnum=" + nodeStatusEnum +
                '}';
    }

}