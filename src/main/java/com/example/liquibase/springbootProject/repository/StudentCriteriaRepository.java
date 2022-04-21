package com.example.liquibase.springbootProject.repository;

import com.example.liquibase.springbootProject.entity.Student;
import com.example.liquibase.springbootProject.entity.StudentPage;
import com.example.liquibase.springbootProject.entity.StudentSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class StudentCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public StudentCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Student> findAllWithFilters(StudentPage studentPage,
                                            StudentSearchCriteria studentSearchCriteria) {
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);
        Predicate predicate = getPredicate(studentSearchCriteria, studentRoot);
        criteriaQuery.where(predicate);

        setOrder(studentPage, criteriaQuery, studentRoot);

        TypedQuery<Student> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(studentPage.getPageNumber() * studentPage.getPageSize());
        typedQuery.setMaxResults(studentPage.getPageSize());

        Pageable pageable = getPageable(studentPage);

        long studentsCount = getStudentsCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, studentsCount);
    }




    private Predicate getPredicate(StudentSearchCriteria studentSearchCriteria,
                                   Root<Student> studentRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(studentSearchCriteria.getFirstName())) {
            predicates.add(
                    criteriaBuilder.like(studentRoot.get("firstName"),
                            "%" + studentSearchCriteria.getFirstName() + "%")
            );
        }

        if (Objects.nonNull(studentSearchCriteria.getLastName())) {
            predicates.add(
                    criteriaBuilder.like(studentRoot.get("lastName"),
                            "%" + studentSearchCriteria.getLastName() + "%")
            );
        }

        if (Objects.nonNull(studentSearchCriteria.getYearOfStudy())) {
            predicates.add(
                    criteriaBuilder.like(studentRoot.get("yearOfStudy").as(String.class),
                            "%" + studentSearchCriteria.getYearOfStudy() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(StudentPage studentPage,
                          CriteriaQuery<Student> criteriaQuery,
                          Root<Student> studentRoot) {
        if (studentPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(studentRoot.get(studentPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(studentRoot.get(studentPage.getSortBy())));
        }
    }

    private Pageable getPageable(StudentPage studentPage) {
        Sort sort = Sort.by(studentPage.getSortDirection(), studentPage.getSortBy());
        return PageRequest.of(studentPage.getPageNumber(), studentPage.getPageSize(), sort);
    }

    private long getStudentsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Student> countRoot = countQuery.from(Student.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();

    }
}
