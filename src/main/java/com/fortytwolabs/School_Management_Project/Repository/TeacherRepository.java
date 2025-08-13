package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntityClass, Integer> {
    //We Can Use Optional Instead.
    List<TeacherEntityClass> findByTeacherEmail(String teacherEmail);

    List<TeacherEntityClass> findByTeacherName(String teacherName);

    TeacherEntityClass findByTeacherId(Long teacherId);

}
