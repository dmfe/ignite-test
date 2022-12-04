package xyz.dmfe.ignite.util;

import xyz.dmfe.ignite.entities.Course;
import xyz.dmfe.ignite.entities.Teacher;

public final class TestCourseFactory {

    private TestCourseFactory() {}

    public static Course createTestCourse() {
        return new Course(
                "Test Course",
                90,
                (short) 90,
                new Teacher("Test Teacher", "test_picture", "test@test.mail")
        );
    }
}
