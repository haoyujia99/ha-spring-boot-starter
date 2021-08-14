package cn.developer.howie.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * cn.developer.howie.util.CommandLineUtils.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/3/2021 8:28 PM
 */
public class CommandLineUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineUtils.class);

    private static final String IF_CONFIG = "ifconfig";
    private static final String ARPING = "arping";

    private CommandLineUtils() throws InstantiationException {
        throw new InstantiationException("Prohibited From Instantiating CommandLineUtils.class");
    }

    public static void unbindVirtualHost(String virtualNetworkInterface, String virtualHostAddress) {

        CommandLine commandLine = new CommandLine(IF_CONFIG);
        commandLine.addArgument(virtualNetworkInterface);
        commandLine.addArgument(virtualHostAddress);
        commandLine.addArgument("down");
        execute(commandLine);
    }

    public static void bindVirtualHost(String virtualNetworkInterface, String virtualHostAddress, String netmask) {

        CommandLine commandLine = new CommandLine(IF_CONFIG);
        commandLine.addArgument(virtualNetworkInterface);
        commandLine.addArgument(virtualHostAddress);
        commandLine.addArgument("netmask");
        commandLine.addArgument(netmask);
        commandLine.addArgument("up");
        execute(commandLine);
    }

    public static void refreshArpCache(String networkInterface, String virtualHostAddress) {

        CommandLine commandLine = new CommandLine(ARPING);
        commandLine.addArgument("-bUA");
        commandLine.addArgument("-c");
        commandLine.addArgument("2");
        commandLine.addArgument("-I");
        commandLine.addArgument(networkInterface);
        commandLine.addArgument(virtualHostAddress);
        execute(commandLine);
    }

    private static void execute(CommandLine commandLine) {

        String property = System.getProperty("os.name");
        if (property.contains("Windows")) {
            logger.info("### doesn't run command {} on windows ###", commandLine);
        } else {
            logger.info("### Execute command: {} ###", commandLine);

            DefaultExecutor defaultExecutor = new DefaultExecutor();
            try {
                defaultExecutor.execute(commandLine);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}