package xyz.dmfe.ignite.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.dmfe.ignite.entities.Teacher;
import xyz.dmfe.ignite.services.TeacherService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/teachers/{id}")
    public Teacher getTeacherById(@PathVariable("id") String id) {
        return teacherService.getTeacherByID(id);
    }

    @PostMapping("/teachers")
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

    @PutMapping("/teachers/{id}")
    public Teacher updateTeacher(@RequestBody Teacher teacher, @PathVariable String id) {
        return teacherService.updateTeacher(id, teacher);
    }

    @DeleteMapping("/teachers/{id}")
    public void deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacher(id);
    }
}
