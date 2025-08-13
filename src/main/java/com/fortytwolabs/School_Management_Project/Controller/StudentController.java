package com.fortytwolabs.School_Management_Project.Controller;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.StudentRepository;
import com.fortytwolabs.School_Management_Project.Repository.SubjectRepository;
import com.fortytwolabs.School_Management_Project.Service.StudentService;
import com.fortytwolabs.School_Management_Project.Service.SubjectService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final StudentRepository studentRepository;


    public StudentController(StudentService studentService, SubjectService subjectService, StudentRepository studentRepository){
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.studentRepository = studentRepository;
    }

    //Get All Students
    @GetMapping
    public List<StudentEntityClass> getAllStudents(){
        return studentService.getAllStudents();
    }

    //Get StudentById
    @GetMapping("/{id}")
    public ResponseEntity<StudentEntityClass> getStudentById(Long id){
        Optional<StudentEntityClass> student = studentRepository.findStudentById(id);
        if(student.isPresent()){
            return ResponseEntity.ok(student.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    //Create A Student
    @PostMapping
    public StudentEntityClass createStudent(@RequestBody StudentEntityClass studentEntityClass){
        return studentService.saveStudent(studentEntityClass);
    }

    //Update A Student Using ID
    @PutMapping("/{id}")
    public ResponseEntity<StudentEntityClass> updateStudent(@PathVariable Long id, @RequestBody StudentEntityClass studentEntityClass){
        StudentEntityClass updatedStudent = studentService.updateStudent(id, studentEntityClass);
        return ResponseEntity.ok(updatedStudent);
    }

    //To add a single Subject To Student
    @PutMapping("/{studentId}/subject")
    public ResponseEntity<StudentEntityClass> addSubjectToStudent(@PathVariable Long studentId, @RequestBody Long subjectId){
        StudentEntityClass studentEntityClass = studentService.getStudentById(studentId);
        Set<SubjectEntityClass> subjects = subjectService.getSubjectsByIds(Collections.singleton(subjectId));
        studentEntityClass.setSubjects(subjects);

        StudentEntityClass updatedStudent = studentService.saveStudent(studentEntityClass);

        return ResponseEntity.ok(updatedStudent);
    }

    @PutMapping("/{studentId}/subjects")
    public ResponseEntity<StudentEntityClass> addSubjects(@PathVariable Long studentId, @RequestBody List<Long> subjectIds){
        StudentEntityClass updatedStudent = studentService.addSubjectsToStudent(studentId, subjectIds);
        return ResponseEntity.ok(updatedStudent);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Long id){
        try{
            studentService.deleteStudentById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}
