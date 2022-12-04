package xyz.dmfe.ignite.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.dmfe.ignite.entities.Course;
import xyz.dmfe.ignite.exceptions.NotFoundException;
import xyz.dmfe.ignite.repositories.CourseRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Cacheable("courses")
    public Course getCourseByID(String id) {
        log.info("Request for course with id: {}", id);
        return courseRepository.findById(UUID.fromString(id)).orElseThrow(() -> {
            String msg = String.format("Course with id %s not found.", id);
            log.error(msg);
            return new NotFoundException(msg);
        });
    }
}
