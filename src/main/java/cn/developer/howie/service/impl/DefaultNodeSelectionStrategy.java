package cn.developer.howie.service.impl;

import cn.developer.howie.model.ao.HeartBeatMessage;
import cn.developer.howie.model.property.HighAvailableProperties;
import cn.developer.howie.service.NodeSelectionStrategy;
import cn.developer.howie.util.IpUtils;

/**
 * cn.developer.howie.service.impl.DefaultNodeSelectionStrategy.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 12:40 PM
 */
public class DefaultNodeSelectionStrategy implements NodeSelectionStrategy {

    private final HighAvailableProperties highAvailableProperties;

    public DefaultNodeSelectionStrategy(HighAvailableProperties highAvailableProperties) {
        this.highAvailableProperties = highAvailableProperties;
    }

    /**
     * choose the master node by customize strategy
     * the default strategy will choose the bigger ip address as master node
     *
     * @param heartBeatMessage received message
     * @return is current service can be master node
     */
    @Override
    public boolean canBeMaster(HeartBeatMessage heartBeatMessage) {

        String targetAddress = heartBeatMessage.getAddress();
        String localHostAddress = IpUtils.getByName(highAvailableProperties.getNetworkInterface());

        String targetReplace = targetAddress.replace(".", "");
        String localReplace = localHostAddress.replace(".", "");
        return Long.parseLong(localReplace) - Long.parseLong(targetReplace) > 0;
    }

}