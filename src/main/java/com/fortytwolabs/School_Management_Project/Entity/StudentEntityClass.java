package com.fortytwolabs.School_Management_Project.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fortytwolabs.School_Management_Project.util.CounterUtil;
import dev.morphia.Datastore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;


import java.util.HashSet;
import java.util.Set;

@Entity
public class StudentEntityClass {

    @Id
    private Long id;
    private String name;
    private String email;

    @Reference(lazy = true)
    private Set<SubjectEntityClass> subjects = new HashSet<>();

    @Reference(lazy = true)
    @JsonManagedReference
    private Set<TeacherEntityClass> teachers = new HashSet<>();

    public StudentEntityClass(){}

    public void generateId(Datastore datastore){
        this.id = CounterUtil.getNextSequence("student_id", datastore);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Set<SubjectEntityClass> getSubjects(){
        return subjects;
    }
    public void setSubjects(Set<SubjectEntityClass> subjects){
        this.subjects=subjects;
    }

    public Set<TeacherEntityClass> getTeachers(){
        return teachers;
    }
    public void setTeachers(Set<TeacherEntityClass> teachers){
        this.teachers=teachers;
    }

}
