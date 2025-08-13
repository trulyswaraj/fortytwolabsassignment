package com.fortytwolabs.School_Management_Project.Controller;

import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.Service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;


    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherEntityClass> getAllTeacher(){
        return teacherService.getAllTeachers();
    }

    @GetMapping("/email")
    public List<TeacherEntityClass> getTeachersByEmail(@RequestParam String email){
        return teacherService.findByTeacherEmail(email);
    }

    @GetMapping("/name")
    public List<TeacherEntityClass> getTeachersByName(@RequestParam String name){
        return teacherService.findByTeacherName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherEntityClass> getTeacherById(@PathVariable Integer id){
        Optional<TeacherEntityClass> teacherEntityClass = teacherService.findTeacherById(id);

        if(teacherEntityClass.isPresent()){
            return ResponseEntity.ok(teacherEntityClass.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public TeacherEntityClass createTeacher(@RequestBody TeacherEntityClass teacherEntityClass){
        return teacherService.save(teacherEntityClass);
    }

    @PutMapping("/{id}")
    public TeacherEntityClass updateTeacher(@PathVariable Integer id, @RequestBody TeacherEntityClass teacherEntityClass){
        TeacherEntityClass updatedTeacher = teacherService.updateTeacher(id, teacherEntityClass);
        return ResponseEntity.ok(updatedTeacher).getBody();
    }

    @PostMapping("/{teacherId}/subjects")
    public ResponseEntity<TeacherEntityClass> assignSubjectToTeacher(@PathVariable Long teacherId, @RequestParam Long subjectId){
        TeacherEntityClass updatedTeacher = teacherService.assignTeacherToSubject(teacherId, subjectId);
        return ResponseEntity.ok(updatedTeacher);
    }

    @PostMapping("/{teacherId}/students")
    public ResponseEntity<TeacherEntityClass> assignTeacherToSubject(@PathVariable Long teacherId, @RequestParam Long studentId){
        TeacherEntityClass teacherEntityClass = teacherService.assignTeacherToStudent(teacherId, studentId);
        return ResponseEntity.ok(teacherEntityClass);
    }

    @PostMapping("/{teacherId}/subject")
    public ResponseEntity<TeacherEntityClass> updateTeacherSubject(@PathVariable Long teacherId, @RequestParam Long subjectId){
        TeacherEntityClass teacherEntityClass = teacherService.updateTeacherSubjects(teacherId, subjectId);
        return ResponseEntity.ok(teacherEntityClass);
    }
}
