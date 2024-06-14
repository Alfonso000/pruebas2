package es.ufv.dis.api_ord.controller;

import es.ufv.dis.api_ord.model.Student;
import es.ufv.dis.api_ord.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add") // Localhost:8081/api/students/add
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        try {
            studentService.addStudent(student);
            return ResponseEntity.ok("Student added successfully.");
        } catch (Exception e) {
            // Loguea la excepción o imprímela en la consola para obtener más detalles
            return ResponseEntity.badRequest().body("Error adding student: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/exportCsv") // Localhost:8081/api/students/exportCsv
    public ResponseEntity<String> exportCsv() {
        System.out.println("Exporting CSV...");
        studentService.exportCsv();
        return ResponseEntity.ok("CSV export successful. Check the 'resources' folder.");
    }
}