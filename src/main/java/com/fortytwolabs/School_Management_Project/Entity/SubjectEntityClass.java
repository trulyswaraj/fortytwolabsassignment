package com.fortytwolabs.School_Management_Project.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)

@Entity
@Table(name = "subjects")
public class SubjectEntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @Column(name = "subject_name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subjects")
    private Set<TeacherEntityClass> teachers = new HashSet<>();

    @ManyToMany(mappedBy = "subjects")
    private Set<StudentEntityClass> students = new HashSet<>();

    public SubjectEntityClass(){

    }

    public Set<TeacherEntityClass> getTeachers(){
        return teachers;
    }
    public void setTeachers(Set<TeacherEntityClass> teachers){
        this.teachers=teachers;
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
