package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntityClass, Long> {

    Optional<StudentEntityClass> findStudentById(Long id);

   // StudentEntityClass findStudentById(Long studentId);

}
