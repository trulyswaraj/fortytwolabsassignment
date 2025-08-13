package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SubjectRepository extends JpaRepository<SubjectEntityClass, Long> {
    List<SubjectEntityClass> findByIdIn(Set<Long> ids);

    List<SubjectEntityClass> findAllById(Iterable<Long> ids);

    SubjectEntityClass findSubjectById(Long subjectId);
}
