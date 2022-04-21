package com.example.liquibase.springbootProject.service;

import com.example.liquibase.springbootProject.entity.Student;
import com.example.liquibase.springbootProject.entity.StudentPage;
import com.example.liquibase.springbootProject.entity.StudentSearchCriteria;
import com.example.liquibase.springbootProject.entity.dto.StudentDTO;
import com.example.liquibase.springbootProject.mapper.NewStudentMapper;
import com.example.liquibase.springbootProject.repository.StudentCriteriaRepository;
import com.example.liquibase.springbootProject.repository.StudentRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentCriteriaRepository studentCriteriaRepository;

    private NewStudentMapper studentMapper = Mappers.getMapper(NewStudentMapper.class);


    public StudentService(StudentRepository studentRepository, StudentCriteriaRepository studentCriteriaRepository) {
        this.studentRepository = studentRepository;


        this.studentCriteriaRepository = studentCriteriaRepository;
    }

    public StudentDTO addStudent(StudentDTO studentDTO) {
        Student newStudent = studentRepository.save(studentMapper.getEntityFromDTO(studentDTO));
        return studentMapper.getDTOFromEntity(newStudent);

    }

    public StudentDTO getStudentById(Integer studentId) {
        Student foundStudent = studentRepository.getOne(studentId);
        return studentMapper.getDTOFromEntity(foundStudent);

    }

//    public void enrollStudent(Integer studentId, Integer courseId) {
//        Course course = courseRepository.getById(courseId);
//        Student student = studentRepository.getById(studentId);
//
//        student.enrollInCourse(course);
//
//        studentRepository.save(student);
//
//    }

    public Page<Student> getStudents(StudentPage studentPage, StudentSearchCriteria studentSearchCriteria){
        return studentCriteriaRepository.findAllWithFilters(studentPage, studentSearchCriteria);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> studentMapper.getDTOFromEntity(student))
                .collect(Collectors.toList());
    }

    public void deleteStudentById(Integer id) {
        studentRepository.deleteById(id);
    }
}
