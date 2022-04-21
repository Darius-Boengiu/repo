package com.example.liquibase.springbootProject.mapper;

import com.example.liquibase.springbootProject.entity.Student;
import com.example.liquibase.springbootProject.entity.dto.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NewStudentMapper {

    @Mapping(ignore = true, target = "cnp")
    StudentDTO getDTOFromEntity(Student student);


    Student getEntityFromDTO(StudentDTO studentDTO);
}
