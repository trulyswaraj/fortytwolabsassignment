package com.fortytwolabs.School_Management_Project.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fortytwolabs.School_Management_Project.util.CounterUtil;
import dev.morphia.Datastore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import java.util.HashSet;
import java.util.Set;

@Entity("teachers")
public class TeacherEntityClass {

    @Id
    private Long teacherId;

    private String teacherName;
    private String teacherEmail;

    @Reference(lazy = true)
    @JsonBackReference
    private Set<StudentEntityClass> students = new HashSet<>();

    @Reference(lazy = true)
    @JsonManagedReference
    private Set<SubjectEntityClass> subjects = new HashSet<>();

    //Default Constructor
    public TeacherEntityClass(){}

    //Parameterized Constructor
    public TeacherEntityClass(String teacherName, String teacherEmail){
        this.teacherName=teacherName;
        this.teacherEmail=teacherEmail;
    }

    public void generateId(Datastore datastore){
        this.teacherId= CounterUtil.getNextSequence("teacher_id", datastore);
    }

    public Long getTeacherId(){
        return teacherId;
    }
    public void setTeacherId(Long teacherId){
        this.teacherId=teacherId;
    }

    public Set<SubjectEntityClass> getSubjects(){
        return subjects;
    }
    public void setSubjects(Set<SubjectEntityClass> subjects){
        this.subjects=subjects;
    }

    public Set<StudentEntityClass> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentEntityClass> students) {
        this.students = students;
    }


    public String getTeacherName(){
        return teacherName;
    }
    public void setTeacherName(String teacherName){
        this.teacherName=teacherName;
    }

    public String getTeacherEmail(){
        return teacherEmail;
    }
    public void setTeacherEmail(String teacherEmail){
        this.teacherEmail=teacherEmail;
    }

}
