package com.example.liquibase.springbootProject.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
@Entity(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "year_of_study")
    private Integer yearOfStudy;
    @Column(name = "cnp")
    private String cnp;
//    @ManyToMany
//    @JoinTable(name = "students_courses",joinColumns = { @JoinColumn(name = "student_id") },
//            inverseJoinColumns = { @JoinColumn(name = "course_id") })
//    private Set<Course> courses = new HashSet<>();
//
//    public void enrollInCourse(Course course) {
//        courses.add(course);
//    }

}
