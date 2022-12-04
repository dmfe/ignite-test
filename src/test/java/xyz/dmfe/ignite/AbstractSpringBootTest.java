package xyz.dmfe.ignite;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import xyz.dmfe.ignite.repositories.CourseRepository;
import xyz.dmfe.ignite.repositories.TeacherRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AbstractSpringBootTest {
    @MockBean
    protected CourseRepository courseRepository;
    @MockBean
    protected TeacherRepository teacherRepository;
}
