package com.example.liquibase.springbootProject.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentSearchCriteria {
    private String firstName;
    private String lastName;
    private Integer yearOfStudy;
}
