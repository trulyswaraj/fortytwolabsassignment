package com.fortytwolabs.School_Management_Project.Service;

import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository=subjectRepository;
    }

    public Set<SubjectEntityClass> getSubjectsByIds(Set<Long> subjectIds){
        List<SubjectEntityClass> subjects = subjectRepository.findByIdIn(subjectIds);
        return new HashSet<>(subjects);
    }

    public SubjectEntityClass saveSubject(SubjectEntityClass subjectEntityClass){
        return subjectRepository.save(subjectEntityClass);
    }

    public List<SubjectEntityClass> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Optional<SubjectEntityClass> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

}
