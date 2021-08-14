package cn.developer.howie.command;

import cn.developer.howie.model.constant.HighAvailableConstant;
import cn.developer.howie.model.enums.NodeStatusEnum;
import cn.developer.howie.service.ServiceInitializationHandler;
import org.springframework.boot.CommandLineRunner;

/**
 * cn.developer.howie.command.HighAvailableStandaloneCommand.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 11:09 AM
 */
public class HighAvailableStandaloneCommand implements CommandLineRunner {

    private final ServiceInitializationHandler serviceInitializationHandler;

    public HighAvailableStandaloneCommand(ServiceInitializationHandler serviceInitializationHandler) {
        this.serviceInitializationHandler = serviceInitializationHandler;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) {

        HighAvailableConstant.NODE_STATUS_ENUM.set(NodeStatusEnum.MASTER);
        serviceInitializationHandler.onMaster();
    }

}