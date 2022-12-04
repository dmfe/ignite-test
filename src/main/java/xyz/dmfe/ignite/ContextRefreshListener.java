package xyz.dmfe.ignite;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import xyz.dmfe.ignite.services.CacheService;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    private final CacheService cacheService;

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        log.info("Configuring ignite caches...");
        cacheService.resetCaches();
        log.info("Caches configuration done.");
    }
}
