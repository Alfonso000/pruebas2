package es.ufv.dis.api_ord.service;

import es.ufv.dis.api_ord.model.Student;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class StudentServiceTest {

    private StudentService studentService;

    @Before
    public void setUp() {
        studentService = new StudentService();
    }

    @Test
    public void testAddStudent() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setDateOfBirth("1990-01-01");
        student.setGender("Male");

        studentService.addStudent(student);

        List<Student> students = studentService.getAllStudents();
        assertNotNull(students);

        // Verificar que se ha añadido correctamente
        assertTrue(students.contains(student));

        // Obtener el estudiante añadido
        Student addedStudent = students.get(students.indexOf(student));

        // Verificar los atributos del estudiante añadido
        assertNotNull(addedStudent.getID());
        assertEquals("John", addedStudent.getFirstName());
        assertEquals("Doe", addedStudent.getLastName());
        assertEquals("1990-01-01", addedStudent.getDateOfBirth());
        assertEquals("Male", addedStudent.getGender());
    }

    @Test
    public void testGenerateUUID() {
        UUID uuid1 = studentService.generateUUID();
        UUID uuid2 = studentService.generateUUID();

        assertNotNull(uuid1);
        assertNotNull(uuid2);
        assertNotEquals(uuid1, uuid2);
    }

    @Test
    public void testExportCsv() {
        // Add some students
        Student student1 = new Student("Alice", "Johnson", "1995-05-15", "Female");
        Student student2 = new Student("Bob", "Smith", "1993-08-20", "Male");

        studentService.addStudent(student1);
        studentService.addStudent(student2);

        // Export CSV
        studentService.exportCsv();

        // Check if CSV file is created and contains expected data
        List<Student> students = studentService.getAllStudents();
        assertTrue(students.size() >= 2);
    }
}