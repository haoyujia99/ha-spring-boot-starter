package cn.developer.howie.model.constant;

import cn.developer.howie.model.enums.NodeStatusEnum;

import java.util.concurrent.atomic.AtomicReference;

/**
 * cn.developer.howie.model.constant.HighAvailableConstant.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/3/2021 8:20 PM
 */
public class HighAvailableConstant {

    private HighAvailableConstant() throws InstantiationException {
        throw new InstantiationException("Prohibited From Instantiating HighAvailableConstant.class");
    }

    public static final AtomicReference<NodeStatusEnum> NODE_STATUS_ENUM = new AtomicReference<>(NodeStatusEnum.PREPARE);

}