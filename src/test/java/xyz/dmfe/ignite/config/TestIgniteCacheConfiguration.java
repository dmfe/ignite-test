package xyz.dmfe.ignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.spring.IgniteClientSpringCacheManager;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.ClientConnectorConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import xyz.dmfe.ignite.config.properties.IgniteProperties;

import java.util.List;

@TestConfiguration
@ConditionalOnProperty(prefix = "ignite", name = "enabled", havingValue = "true")
@EnableCaching
public class TestIgniteCacheConfiguration {

    @Bean
    public ClientConfiguration clientConfiguration(IgniteProperties properties) {
        return new ClientConfiguration()
                .setAddresses(String.format("localhost:%s", properties.getClientPort()))
                .setTimeout(properties.getClient().getTimeout())
                .setTcpNoDelay(properties.getClient().isNoTcpDelay())
                .setPartitionAwarenessEnabled(true);
    }

    @Bean
    public IgniteConfiguration igniteConfiguration(IgniteProperties properties) {
        ClientConnectorConfiguration clientConnectorConfiguration = new ClientConnectorConfiguration()
                .setPort(properties.getClientPort());

        return new IgniteConfiguration()
                .setIgniteInstanceName(properties.getCacheInstanceName())
                .setClientConnectorConfiguration(clientConnectorConfiguration)
                .setDiscoverySpi(getTcpDiscoverySpi(properties));
    }

    @Bean
    public Ignite ignite(IgniteConfiguration configuration) {
        return Ignition.getOrStart(configuration);
    }

    @Bean
    @DependsOn("ignite")
    public IgniteClient igniteClient(ClientConfiguration clientConfiguration) {
        return Ignition.startClient(clientConfiguration);
    }

    @Bean
    public IgniteClientSpringCacheManager cacheManager(IgniteClient igniteClient) {
        return new IgniteClientSpringCacheManager()
                .setClientInstance(igniteClient);
    }

    private TcpDiscoverySpi getTcpDiscoverySpi(IgniteProperties properties) {
        TcpDiscoveryVmIpFinder tcpFinder = new TcpDiscoveryVmIpFinder()
                .setAddresses(List.of(properties.getServer()));

        return new TcpDiscoverySpi()
                .setNetworkTimeout(properties.getTimeout())
                .setIpFinder(tcpFinder);
    }
}
