package es.ufv.dis.api_ord.service;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import es.ufv.dis.api_ord.model.Student;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    private static final String JSON_FILE_PATH = "src/main/resources/static/archivo.json";
    private static final String CSV_EXPORT_PATH = "src/main/resources/static/archivo.csv";

    private List<Student> students;

    public StudentService() {
        // Inicializar la lista de estudiantes y cargar desde el archivo JSON al iniciar el servicio
        this.students = new ArrayList<>();
        loadStudentsFromJson();
    }

    public void addStudent(Student student) {
        // Se genera un ID automático para cada estudiante y se agrega a la lista
        student.setID(UUID.randomUUID());
        students.add(student);

        // Guardar la lista de estudiantes en el archivo JSON
        saveStudentsToJson();
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public ResponseEntity<Resource> downloadCsv() {
        exportCsv(); // Aseguramos que el archivo CSV esté actualizado antes de descargarlo

        File file = new File(CSV_EXPORT_PATH);
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error downloading CSV file: ", e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=student_roster.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    public void exportCsv() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_EXPORT_PATH))) {
            // Escribir encabezados
            String[] headers = {"First Name", "Last Name", "Date of Birth", "Gender", "UUID"};
            writer.writeNext(headers); // Escribe los encabezados, que son los nombres de las columnas

            // Escribir datos de estudiantes
            for (Student student : students) {
                String[] data = {student.getFirstName(), student.getLastName(), student.getDateOfBirth(),
                        student.getGender(), student.getID().toString()};
                writer.writeNext(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error exporting CSV file: ", e);
        }
    }

    private void loadStudentsFromJson() {
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            Gson gson = new Gson();
            Student[] studentArray = gson.fromJson(reader, Student[].class);
            if (studentArray != null) {
                students = new ArrayList<>(List.of(studentArray));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading students from JSON file: ", e);
        }
    }

    void saveStudentsToJson() {
        try (Writer writer = new FileWriter(JSON_FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(students, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error saving students to JSON file: ", e);
        }
    }

    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
