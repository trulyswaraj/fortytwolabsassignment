package com.fortytwolabs.School_Management_Project.Controller;

import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService){
        this.subjectService=subjectService;
    }

    @PostMapping
    public List<SubjectEntityClass> createSubject(@RequestBody SubjectEntityClass subjectEntityClass){
        return Collections.singletonList(subjectService.saveSubject(subjectEntityClass));
    }

    @GetMapping
    public List<SubjectEntityClass> getAllSubjects(){
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectEntityClass> getSubjectById(@PathVariable Long id){
        Optional<SubjectEntityClass> subject = subjectService.getSubjectById(id);

        if(subject.isPresent()){
            return ResponseEntity.ok(subject.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
