package xyz.dmfe.ignite.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "ignite")
public class IgniteProperties {

    @Data
    public static class Client {
        private int timeout;
        private boolean noTcpDelay;
    }

    private String connectionString;
    private String Server;
    private String cacheInstanceName;
    private int clientPort;
    private long timeout;
    private boolean enabled;
    private Client client;
    private Map<String, Long> expirePolicies;
}
