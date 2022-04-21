--liquibase formatted sql

--changeset your.name:1 labels:example-label context:example-context
--comment: example comment
CREATE TABLE students (
  student_id serial PRIMARY KEY,
  first_name VARCHAR(45) NULL,
  last_name VARCHAR(45) NULL,
  year_of_study INT NULL,
  gender VARCHAR(45) NULL,
  cnp VARCHAR(13) NULL
);
--rollback DROP TABLE students;





