package xyz.dmfe.ignite.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class NamesService {

    private final CacheService cacheService;

    public String getName() {
        return cacheService.getCachedValue("names", "test-key", key -> chooseRandomName());
    }

    private String chooseRandomName() {
        log.info("Choosing name...");

        List<String> names = List.of(
                "Ivan",
                "Dmitry",
                "Igor",
                "Vlad",
                "Stepan",
                "Alexey",
                "Semen",
                "Anatoly",
                "Timofei",
                "Aristarh",
                "Vladimir"
        );

        simulateLongOperation();

        return names.get(new Random().nextInt(names.size()));
    }

    private void simulateLongOperation() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            log.error("Error during simulation: " + ex.getLocalizedMessage(), ex);
        }
    }
}
