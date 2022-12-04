package xyz.dmfe.ignite.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientCacheConfiguration;
import org.apache.ignite.client.ClientException;
import org.apache.ignite.client.IgniteClient;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheService {

    private final IgniteClient igniteClient;
    private final ClientCacheConfiguration[] clientCacheConfigurations;

    public <K, V> V getCachedValue(String cacheName, K key, Function<K, V> valueFunction) {
        ClientCache<K, V> clientCache = igniteClient.getOrCreateCache(cacheName);
        if (!clientCache.containsKey(key)) {
            V value = valueFunction.apply(key);
            clientCache.putIfAbsent(key, value);
            return value;
        } else {
            return clientCache.get(key);
        }
    }

    public void resetCaches() {
        for (ClientCacheConfiguration clientCacheConfiguration : clientCacheConfigurations) {
            try {
                igniteClient.destroyCache(clientCacheConfiguration.getName());
            } catch (ClientException ex) {
                log.warn("Error during cache {} creation: {}",
                        clientCacheConfiguration.getName(), ex.getLocalizedMessage());
            }
            igniteClient.getOrCreateCache(clientCacheConfiguration);
        }
    }
}
