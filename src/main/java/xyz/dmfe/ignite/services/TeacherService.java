package xyz.dmfe.ignite.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.dmfe.ignite.entities.Teacher;
import xyz.dmfe.ignite.exceptions.NotFoundException;
import xyz.dmfe.ignite.repositories.TeacherRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Cacheable("teachers")
    public Teacher getTeacherByID(String id) {
        log.info("Request: find teacher with id: {}", id);
        return teacherRepository.findById(UUID.fromString(id)).orElseThrow(() -> {
            String msg = String.format("Teacher with id %s not found.", id);
            log.error(msg);
            return new NotFoundException(msg);
        });
    }

    public Teacher createTeacher(Teacher teacher) {
        log.info("Request: create new teacher");
        return teacherRepository.save(teacher);
    }

    @CachePut(value = "teachers", key = "#id")
    public Teacher updateTeacher(String id, Teacher updatedTeacher) {
        log.info("Request: update teacher with id: {}", id);
        Teacher foundTeacher = teacherRepository.findById(UUID.fromString(id)).orElseThrow(() -> {
            String msg = String.format("Teacher with id %s not found.", id);
            log.error(msg);
            return new NotFoundException(msg);
        });
        updatedTeacher.setId(foundTeacher.getId());
        return teacherRepository.save(updatedTeacher);
    }

    @CacheEvict("teachers")
    public void deleteTeacher(String id) {
        log.info("Request: delete teacher with id: {}", id);
        Teacher foundTeacher = teacherRepository.findById(UUID.fromString(id)).orElseThrow(() -> {
            String msg = String.format("Teacher with id %s not found.", id);
            log.error(msg);
            return new NotFoundException(msg);
        });
        teacherRepository.delete(foundTeacher);
    }
}
