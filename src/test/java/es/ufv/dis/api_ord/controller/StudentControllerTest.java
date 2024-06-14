package es.ufv.dis.api_ord.controller;

import es.ufv.dis.api_ord.model.Student;
import es.ufv.dis.api_ord.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Before
    public void setUp() {
        try {
            MockitoAnnotations.openMocks(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public <T> void testAddStudent() {
        Student student = new Student("John", "Doe", "2000-01-01", "Male", UUID.randomUUID());

        ResponseEntity<String> response = studentController.addStudent(student);

        assertEquals("Student added successfully: \n" + student.toString(), response.getBody());
        verify(studentService, times(1)).addStudent(any());
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = List.of(new Student("John", "Doe", "2000-01-01", "Male", UUID.randomUUID()));
        when(studentService.getAllStudents()).thenReturn(students);

        ResponseEntity<List<Student>> response = studentController.getAllStudents();

        assertEquals(students, response.getBody());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    public void testExportCsv() {
        ResponseEntity<String> response = studentController.exportCsv();

        assertEquals("CSV export successful. Check the 'exports' folder.", response.getBody());
        verify(studentService, times(1)).exportCsv();
    }
}