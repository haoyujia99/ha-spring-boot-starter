package cn.developer.howie.command;

import cn.developer.howie.service.HighAvailableService;
import org.springframework.boot.CommandLineRunner;

/**
 * cn.developer.howie.command.HighAvailableCommand.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 11:09 AM
 */
public class HighAvailableCommand implements CommandLineRunner {

    private final HighAvailableService highAvailableService;

    public HighAvailableCommand(HighAvailableService highAvailableService) {
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