package xyz.dmfe.ignite.config;

import org.apache.ignite.Ignition;
import org.apache.ignite.cache.spring.IgniteClientSpringCacheManager;
import org.apache.ignite.client.ClientCacheConfiguration;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import xyz.dmfe.ignite.config.properties.IgniteProperties;

import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "ignite", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(IgniteProperties.class)
public class IgniteCacheConfiguration {

    @Bean
    @Profile("!test")
    public ClientConfiguration clientConfiguration(IgniteProperties properties) {
        return new ClientConfiguration()
                .setAddresses(properties.getConnectionString().split(","))
                .setTimeout(properties.getClient().getTimeout())
                .setTcpNoDelay(properties.getClient().isNoTcpDelay())
                .setPartitionAwarenessEnabled(true);
    }

    @Bean
    public ClientCacheConfiguration[] clientCacheConfigurations(IgniteProperties properties) {
        return properties.getExpirePolicies().entrySet().stream()
                .map(policyEntry -> new ClientCacheConfiguration()
                        .setName(policyEntry.getKey())
                        .setExpiryPolicy(TouchedExpiryPolicy.factoryOf(
                                new Duration(TimeUnit.MILLISECONDS, policyEntry.getValue())
                        ).create())
                ).toArray(ClientCacheConfiguration[]::new);
    }

    @Bean
    @Profile("!test")
    public IgniteClient igniteClient(ClientConfiguration clientConfiguration) {
        return Ignition.startClient(clientConfiguration);
    }

    @Bean
    @Profile("!test")
    public IgniteClientSpringCacheManager cacheManager(IgniteClient igniteClient) {
        return new IgniteClientSpringCacheManager()
                .setClientInstance(igniteClient);
    }
}
