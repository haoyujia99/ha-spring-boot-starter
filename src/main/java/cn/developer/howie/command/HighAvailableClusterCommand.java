package cn.developer.howie.command;

import cn.developer.howie.service.HighAvailableService;
import org.springframework.boot.CommandLineRunner;

/**
 * cn.developer.howie.command.HighAvailableClusterCommand.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/14/2021 10:09 AM
 */
public class HighAvailableClusterCommand implements CommandLineRunner {

    private final HighAvailableService highAvailableService;

    public HighAvailableClusterCommand(HighAvailableService highAvailableService) {
        this.highAvailableService = highAvailableService;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) throws Exception {
        highAvailableService.ready();
    }

}