package com.fortytwolabs.School_Management_Project.Resources;

import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.Service.TeacherService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Path("/api/teachers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherResource {

    private final TeacherService teacherService;


    public TeacherResource(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GET
    public List<TeacherEntityClass> getAllTeacher(){
        return teacherService.getAllTeachers();
    }

    @GET
    @Path("/email")
    public List<TeacherEntityClass> getTeachersByEmail(@QueryParam("email") String email){
        return teacherService.findByTeacherEmail(email);
    }

    @GET
    @Path("/name")
    public List<TeacherEntityClass> getTeachersByName(@QueryParam("name") String name){
        return teacherService.findByTeacherName(name);
    }

    @GET
    @Path("/{id}")
    public ResponseEntity<TeacherEntityClass> getTeacherById(@PathParam("id") Integer id){
        Optional<TeacherEntityClass> teacherEntityClass = teacherService.findTeacherById(id);

        if(teacherEntityClass.isPresent()){
            return ResponseEntity.ok(teacherEntityClass.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @POST
    public TeacherEntityClass createTeacher( TeacherEntityClass teacherEntityClass){
        return teacherService.save(teacherEntityClass);
    }

    @PUT
    @Path("/{id}")
    public TeacherEntityClass updateTeacher(@PathParam("id") Integer id, TeacherEntityClass teacherEntityClass){
        TeacherEntityClass updatedTeacher = teacherService.updateTeacher(id, teacherEntityClass);
        return ResponseEntity.ok(updatedTeacher).getBody();
    }

    @POST
    @Path("/{teacherId}/subjects")
    public ResponseEntity<TeacherEntityClass> assignSubjectToTeacher(@PathParam("teacherId") Long teacherId, @QueryParam("subjectId") Long subjectId){
        TeacherEntityClass updatedTeacher = teacherService.assignTeacherToSubject(teacherId, subjectId);
        return ResponseEntity.ok(updatedTeacher);
    }

    @POST
    @Path("/{teacherId}/students")
    public ResponseEntity<TeacherEntityClass> assignTeacherToStudent(@PathParam("teacherId") Long teacherId, @QueryParam("studentId") Long studentId){
        TeacherEntityClass teacherEntityClass = teacherService.assignTeacherToStudent(teacherId, studentId);
        return ResponseEntity.ok(teacherEntityClass);
    }

    @POST
    @Path("/{teacherId}/subject")
    public ResponseEntity<TeacherEntityClass> updateTeacherSubject(@PathParam("teacherId") Long teacherId, @QueryParam("subjectId") Long subjectId){
        TeacherEntityClass teacherEntityClass = teacherService.updateTeacherSubjects(teacherId, subjectId);
        return ResponseEntity.ok(teacherEntityClass);
    }
}
