package xyz.dmfe.ignite.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.dmfe.ignite.entities.Course;
import xyz.dmfe.ignite.services.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoursesController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable("id") String id) {
        return courseService.getCourseByID(id);
    }
}
