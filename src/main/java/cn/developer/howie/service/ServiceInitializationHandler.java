package cn.developer.howie.service;

import cn.developer.howie.model.exception.HighAvailableException;
import org.springframework.scheduling.annotation.Async;

/**
 * cn.developer.howie.service.ServiceInitializationHandler.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 11:53 AM
 */
public interface ServiceInitializationHandler {

    /**
     * async execute customize method when service becomes master node
     *
     * @throws HighAvailableException the subclass of RuntimeException
     */
    @Async
    void onMaster() throws HighAvailableException;

    /**
     * async execute customize method when service becomes slave node
     *
     * @throws HighAvailableException the subclass of RuntimeException
     */
    @Async
    void onSlave() throws HighAvailableException;

}