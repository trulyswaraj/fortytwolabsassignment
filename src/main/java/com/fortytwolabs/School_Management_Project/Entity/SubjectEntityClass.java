package com.fortytwolabs.School_Management_Project.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fortytwolabs.School_Management_Project.util.CounterUtil;
import dev.morphia.Datastore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;


import java.util.HashSet;
import java.util.Set;

@Entity("subjects")
public class SubjectEntityClass {

    @Id
    private Long id;
    private String name;

    @Reference
    @JsonBackReference
    private Set<TeacherEntityClass> teachers = new HashSet<>();

    @Reference
    private Set<StudentEntityClass> students = new HashSet<>();

    public SubjectEntityClass(){}

    public void generateId(Datastore datastore){
        this.id= CounterUtil.getNextSequence("subject_id", datastore);
    }

    public Set<TeacherEntityClass> getTeachers(){
        return teachers;
    }
    public void setTeachers(Set<TeacherEntityClass> teachers){
        this.teachers=teachers;
    }

    public Set<StudentEntityClass> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentEntityClass> students) {
        this.students = students;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
