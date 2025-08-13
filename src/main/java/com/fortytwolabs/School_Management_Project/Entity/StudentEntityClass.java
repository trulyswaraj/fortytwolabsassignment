package com.fortytwolabs.School_Management_Project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="students")
public class StudentEntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Long id;

    @Column(name="student_name")
    private String name;
    @Column(name="student_email")
    private String email;


    //Many-To-Many relationship which depicts many students can have many subjects and vice versa is true too
    //Just the fetch typ lazy says that when ever the set of students is loaded it will only load the students not the whole students+subjects,
    //instead it will load the subjects when getSubjects method is called. and if a new student is added,
    //we need to add the student and subject separately if persist is not used and if it is used we dont need to manually map the subject and students.
    //and if we use merge then what ever changes are done in the subject for a student the changes or updates are done automatically.
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name="student_subject",
            joinColumns = @JoinColumn(name="student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<SubjectEntityClass> subjects = new HashSet<>();


    //MANY-TO-MANY mapping for Multiple Students can have Multiple Teachers and vice versa.
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(
            name="students_teachers",
            joinColumns = @JoinColumn(name="student_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<TeacherEntityClass> teachers = new HashSet<>();

    public StudentEntityClass(){

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
