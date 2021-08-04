package cn.developer.howie.config;

import cn.developer.howie.command.HighAvailableCommand;
import cn.developer.howie.model.property.HighAvailableProperties;
import cn.developer.howie.service.HighAvailableService;
import cn.developer.howie.service.NodeSelectionStrategy;
import cn.developer.howie.service.ServiceInitializationHandler;
import cn.developer.howie.service.impl.DefaultNodeSelectionStrategy;
import cn.developer.howie.service.impl.DefaultServiceInitializationHandler;
import cn.developer.howie.service.impl.HighAvailableServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * HighAvailableAutoConfiguration.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 9:55 AM
 */
@EnableAsync
@EnableConfigurationProperties(value = HighAvailableProperties.class)
@ConditionalOnProperty(name = "ha.enable", havingValue = "true")
@Configuration
public class HighAvailableAutoConfiguration {

    @ConditionalOnMissingBean(value = ServiceInitializationHandler.class)
    @Bean
    public ServiceInitializationHandler serviceInitializationHandler() {
        return new DefaultServiceInitializationHandler();
    }

    @ConditionalOnMissingBean(value = NodeSelectionStrategy.class)
    @Bean
    public NodeSelectionStrategy nodeSelectionStrategy(HighAvailableProperties highAvailableProperties) {
        return new DefaultNodeSelectionStrategy(highAvailableProperties);
    }

    @Bean
    public HighAvailableService highAvailableProperties(
            HighAvailableProperties highAvailableProperties,
            ServiceInitializationHandler serviceInitializationHandler,
            NodeSelectionStrategy nodeSelectionStrategy) {
        return new HighAvailableServiceImpl(highAvailableProperties, serviceInitializationHandler, nodeSelectionStrategy);
    }

    @Bean
    public HighAvailableCommand highAvailableCommand(HighAvailableService highAvailableService) {
        return new HighAvailableCommand(highAvailableService);
    }

}