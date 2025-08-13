package com.fortytwolabs.School_Management_Project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="teachers")
public class TeacherEntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="teacher_id", nullable = false)
    private Long teacherId;

    @Column(name="teacher_name", nullable = false, length = 150)
    private String teacherName;

    @Column(name="teacher_email", nullable = false, unique = true, length = 150)
    private String teacherEmail;

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
//    @JsonIgnore
    private Set<StudentEntityClass> students = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name="teacher_id"),
            inverseJoinColumns = @JoinColumn(name="subject_id")
    )
    private Set<SubjectEntityClass> subjects = new HashSet<>();

    //Default Constructor
    public TeacherEntityClass(){

    }

    //Parameterized Constructor
    public TeacherEntityClass(String teacherName, String teacherEmail){
        this.teacherName=teacherName;
        this.teacherEmail=teacherEmail;
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
