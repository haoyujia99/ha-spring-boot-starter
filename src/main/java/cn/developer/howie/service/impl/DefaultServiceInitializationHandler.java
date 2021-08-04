package cn.developer.howie.service.impl;

import cn.developer.howie.model.exception.HighAvailableException;
import cn.developer.howie.service.ServiceInitializationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * cn.developer.howie.service.impl.DefaultServiceInitializationHandler.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 12:48 PM
 */
public class DefaultServiceInitializationHandler implements ServiceInitializationHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceInitializationHandler.class);

    /**
     * async execute customize method when service becomes master node
     *
     * @throws HighAvailableException the subclass of RuntimeException
     */
    @Override
    public void onMaster() throws HighAvailableException {
        logger.info("### I'm master ###");
    }

    /**
     * async execute customize method when service becomes slave node
     *
     * @throws HighAvailableException the subclass of RuntimeException
     */
    @Override
    public void onSlave() throws HighAvailableException {
        logger.info("### I'm slave ###");
    }

}