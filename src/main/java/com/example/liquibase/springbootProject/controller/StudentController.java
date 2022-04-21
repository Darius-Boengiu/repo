package com.example.liquibase.springbootProject.controller;

import com.example.liquibase.springbootProject.entity.Student;
import com.example.liquibase.springbootProject.entity.StudentPage;
import com.example.liquibase.springbootProject.entity.StudentSearchCriteria;
import com.example.liquibase.springbootProject.entity.dto.StudentDTO;
import com.example.liquibase.springbootProject.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/")
    public ResponseEntity<StudentDTO> add(@RequestBody @Valid StudentDTO studentDTO) {
        return new ResponseEntity<>(studentService.addStudent(studentDTO), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

//    @PostMapping("{student_id}/course/{course_id}")
//    public void enrollStudent(@PathVariable("student_id") Integer studentId, @PathVariable("course_id") Integer courseId) {
//        studentService.enrollStudent(studentId, courseId);
//    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Student>> getStudents(StudentPage studentPage, StudentSearchCriteria studentSearchCriteria) {
        return new ResponseEntity<>(studentService.getStudents(studentPage, studentSearchCriteria), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
    }

}
