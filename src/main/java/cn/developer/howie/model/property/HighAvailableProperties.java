package cn.developer.howie.model.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * HighAvailableProperties.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 9:55 AM
 */
@ConfigurationProperties(prefix = "ha")
public class HighAvailableProperties {

    private Boolean enable;
    private String virtualHostAddress;
    private List<String> hostAddresses;
    private Integer heartbeatPort;
    private String networkInterface;
    private String virtualNetworkInterface;
    private String netmask;
    private Integer heartbeatInterval;
    private Integer heartbeatTimeout;
    private Integer initializationTimeWait;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getVirtualHostAddress() {
        return virtualHostAddress;
    }

    public void setVirtualHostAddress(String virtualHostAddress) {
        this.virtualHostAddress = virtualHostAddress;
    }

    public List<String> getHostAddresses() {
        return hostAddresses;
    }

    public void setHostAddresses(List<String> hostAddresses) {
        this.hostAddresses = hostAddresses;
    }

    public Integer getHeartbeatPort() {
        return heartbeatPort;
    }

    public void setHeartbeatPort(Integer heartbeatPort) {
        this.heartbeatPort = heartbeatPort;
    }

    public String getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(String networkInterface) {
        this.networkInterface = networkInterface;
    }

    public String getVirtualNetworkInterface() {
        return virtualNetworkInterface;
    }

    public void setVirtualNetworkInterface(String virtualNetworkInterface) {
        this.virtualNetworkInterface = virtualNetworkInterface;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public Integer getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(Integer heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public Integer getHeartbeatTimeout() {
        return heartbeatTimeout;
    }

    public void setHeartbeatTimeout(Integer heartbeatTimeout) {
        this.heartbeatTimeout = heartbeatTimeout;
    }

    public Integer getInitializationTimeWait() {
        return initializationTimeWait;
    }

    public void setInitializationTimeWait(Integer initializationTimeWait) {
        this.initializationTimeWait = initializationTimeWait;
    }

    @Override
    public String toString() {
        return "HighAvailableProperties{" +
                "enable=" + enable +
                ", virtualHostAddress='" + virtualHostAddress + '\'' +
                ", hostAddresses=" + hostAddresses +
                ", heartbeatPort=" + heartbeatPort +
                ", networkInterface='" + networkInterface + '\'' +
                ", virtualNetworkInterface='" + virtualNetworkInterface + '\'' +
                ", netmask='" + netmask + '\'' +
                ", heartbeatInterval=" + heartbeatInterval +
                ", heartbeatTimeout=" + heartbeatTimeout +
                ", initializationTimeWait=" + initializationTimeWait +
                '}';
    }

}