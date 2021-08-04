package cn.developer.howie.model.enums;

/**
 * cn.developer.howie.model.NodeStatusEnum.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/3/2021 8:25 PM
 */
public enum NodeStatusEnum {

    /**
     * HA function init
     */
    PREPARE,

    /**
     * master
     */
    MASTER,

    /**
     * slave
     */
    SLAVE

}