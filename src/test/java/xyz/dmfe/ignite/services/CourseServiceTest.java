package xyz.dmfe.ignite.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import xyz.dmfe.ignite.AbstractSpringBootTest;
import xyz.dmfe.ignite.config.TestIgniteCacheConfiguration;
import xyz.dmfe.ignite.entities.Course;
import xyz.dmfe.ignite.util.TestCourseFactory;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class
})
@Import(TestIgniteCacheConfiguration.class)
class CourseServiceTest extends AbstractSpringBootTest {

    @Autowired
    private CourseService courseService;

    @MockBean
    private NamesService namesService;

    @BeforeAll
    public void setUp() {
        when(courseRepository.findById(any(UUID.class))).thenReturn(Optional.of(TestCourseFactory.createTestCourse()));
    }

    @Test
    @DisplayName("Get course by id test.")
    public void testGetCourseById() {
        Course actualCourse = courseService.getCourseByID("ef744502-29a6-4aff-ad6a-c2f07fd5c12b");
        log.info("found course: {}", actualCourse);
        actualCourse = courseService.getCourseByID("ef744502-29a6-4aff-ad6a-c2f07fd5c12b");
        log.info("found course: {}", actualCourse);
        actualCourse = courseService.getCourseByID("84cf11ee-b773-4690-8b5c-6fa8be8c7067");
        log.info("found course: {}", actualCourse);
        actualCourse = courseService.getCourseByID("84cf11ee-b773-4690-8b5c-6fa8be8c7067");
        log.info("found course: {}", actualCourse);

        assertThat(actualCourse).usingRecursiveComparison()
                .ignoringFields("id", "teacher.id")
                .isEqualTo(TestCourseFactory.createTestCourse());
    }
}
