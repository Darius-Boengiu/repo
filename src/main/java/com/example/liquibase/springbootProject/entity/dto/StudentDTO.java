package com.example.liquibase.springbootProject.entity.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class StudentDTO {


    private Integer studentId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private Integer yearOfStudy;
    @NotNull
    @Size(min = 13, max = 13)
    private String cnp;
}
