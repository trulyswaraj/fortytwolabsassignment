package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.List;

public class StudentDao{
    private final Datastore datastore;

    public StudentDao(Datastore datastore){
        this.datastore=datastore;
    }

    public void save(StudentEntityClass student){
        student.generateId(datastore);
        datastore.save(student);
    }

    public StudentEntityClass getById(Long id){
        return datastore.find(StudentEntityClass.class)
                .filter(Filters.eq("_id", id))
                .first();
    }

    public List<StudentEntityClass> getAllStudents(){
        return datastore.find(StudentEntityClass.class)
                .iterator().toList();
    }

    public List<StudentEntityClass> getAllStudents(int page, int size){
        int skip = (page - 1) * size;

        return datastore.find(StudentEntityClass.class)
                .stream().skip(skip).limit(size).toList();
    }

    public long getTotalStudents(){
        return datastore.find(StudentEntityClass.class).count();
    }

    public StudentEntityClass updateStudent(Long id, StudentEntityClass updated){
        StudentEntityClass existing = getById(id);

        if(existing == null){
            return null;
        }
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());

        datastore.save(existing);
        return existing;
    }

    public void delete(Long id){
        datastore.find(StudentEntityClass.class)
                .filter(Filters.eq("_id", id))
                .delete();
    }

    public StudentEntityClass assignTeacherToStudent(Long studentId, TeacherEntityClass teacher) {
        StudentEntityClass student = getById(studentId);

        if (student == null) {
            return null;
        }

        // Add teacher to student's set
        student.getTeachers().add(teacher);

        // (optional) Add student to teacher’s set if you want bidirectional relation
        teacher.getStudents().add(student);

        datastore.save(student);
        datastore.save(teacher);

        return student;
    }

    public StudentEntityClass assignSubjectToStudent(Long studentId, SubjectEntityClass subject) {
        StudentEntityClass student = getById(studentId);
        if (student == null) return null;

        student.getSubjects().add(subject);
        subject.getStudents().add(student);

        datastore.save(student);
        datastore.save(subject);

        return student;
    }

}