package xyz.dmfe.ignite.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Import;
import xyz.dmfe.ignite.AbstractSpringBootTest;
import xyz.dmfe.ignite.config.TestIgniteCacheConfiguration;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class
})
@Import(TestIgniteCacheConfiguration.class)
public class NamesServiceTest extends AbstractSpringBootTest {

    @Autowired
    private NamesService namesService;

    @Test
    public void testGetName() throws Exception {
        log.info("name: {}", namesService.getName());
        log.info("name: {}", namesService.getName());
        Thread.sleep(10000);
        log.info("name: {}", namesService.getName());
    }
}
