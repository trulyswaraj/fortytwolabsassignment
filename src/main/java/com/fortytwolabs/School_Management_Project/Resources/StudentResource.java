package com.fortytwolabs.School_Management_Project.Resources;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.StudentRepository;
import com.fortytwolabs.School_Management_Project.Service.StudentService;
import com.fortytwolabs.School_Management_Project.Service.SubjectService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Path("/api/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final StudentRepository studentRepository;


    public StudentResource(StudentService studentService, SubjectService subjectService, StudentRepository studentRepository){
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.studentRepository = studentRepository;
    }

    //Get All Students
    @GET
    public List<StudentEntityClass> getAllStudents(){
        return studentService.getAllStudents();
    }

    //Get StudentById
    @GET
    @Path("/{id}")
    public ResponseEntity<StudentEntityClass> getStudentById(@PathParam("id") Long id){
        Optional<StudentEntityClass> student = studentRepository.findStudentById(id);
        if(student.isPresent()){
            return ResponseEntity.ok(student.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    //Create A Student
    @POST
    public StudentEntityClass createStudent(StudentEntityClass studentEntityClass){
        return studentService.saveStudent(studentEntityClass);
    }

    //Update A Student Using ID
    @PUT
    @Path("/{id}")
    public ResponseEntity<StudentEntityClass> updateStudent(@PathParam("id") Long id, StudentEntityClass studentEntityClass){
        StudentEntityClass updatedStudent = studentService.updateStudent(id, studentEntityClass);
        return ResponseEntity.ok(updatedStudent);
    }

    //To add a single Subject To Student
    @PUT
    @Path("/{studentId}/subject")
    public ResponseEntity<StudentEntityClass> addSubjectToStudent(@PathParam("studentId") Long studentId, Long subjectId){
        StudentEntityClass studentEntityClass = studentService.getStudentById(studentId);
        Set<SubjectEntityClass> subjects = subjectService.getSubjectsByIds(Collections.singleton(subjectId));
        studentEntityClass.setSubjects(subjects);

        StudentEntityClass updatedStudent = studentService.saveStudent(studentEntityClass);

        return ResponseEntity.ok(updatedStudent);
    }

    @PUT
    @Path("/{studentId}/subjects")
    public ResponseEntity<StudentEntityClass> addSubjects(@PathParam("studentId") Long studentId, List<Long> subjectIds){
        StudentEntityClass updatedStudent = studentService.addSubjectsToStudent(studentId, subjectIds);
        return ResponseEntity.ok(updatedStudent);
    }


    @DELETE
    @Path("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathParam("id") Long id){
        try{
            studentService.deleteStudentById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}
