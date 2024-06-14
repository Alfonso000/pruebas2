package es.ufv.dis.api_ord.model;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class StudentTest {
    @Test
    public void testStudentToString() {
        UUID id = UUID.randomUUID();
        Student student = new Student("John", "Doe", "2000-01-01", "Male", id);
        String expected = "Student{" +
                "firstName='John', " +
                "lastName='Doe', " +
                "dateOfBirth='2000-01-01', " +
                "gender='Male', " +
                "ID=" + id +
                '}';
        assertEquals(expected, student.toString());
    }
}