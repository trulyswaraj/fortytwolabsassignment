package com.fortytwolabs.School_Management_Project.Service;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.*;

import java.util.List;

public class TeacherService {

    private TeacherDao teacherDao;
    private SubjectDao subjectDao;
    private StudentDao studentDao;

    public TeacherService(){
        this.teacherDao = new TeacherDao();
        this.subjectDao= new SubjectDao();
        this.studentDao= new StudentDao();
    }

    public void addTeacher(TeacherEntityClass teacherEntityClass){
        teacherDao.save(teacherEntityClass);
    }

    public TeacherEntityClass getTeacherById(Long id){
        return teacherDao.getById(id);
    }

    public List<TeacherEntityClass> getAllTeachers(){
        return teacherDao.getAll();
    }

    public void updateTeacher(TeacherEntityClass teacherEntityClass){
        teacherDao.updateTeacher(teacherEntityClass);
    }

    public void deleteTeacher(Long id){
        teacherDao.delete(id);
    }

    public SubjectEntityClass getSubjectById(Long id) {
        return subjectDao.getById(id);
    }

    public StudentEntityClass getStudentById(Long id) {
        return studentDao.getById(id);
    }


    public TeacherEntityClass assignTeacherToSubject(Long teacherId, SubjectEntityClass subjectEntityClass){
        return teacherDao.assignTeacherToSubject(teacherId, subjectEntityClass);
    }

    public TeacherEntityClass assignTeacherToStudent(Long teacherId, Long studentId) {
        // Fetch the student entity first
        StudentEntityClass student = studentDao.getById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }
        return teacherDao.assignTeacherToStudent(teacherId, student);
    }


    public TeacherEntityClass updateTeacherSubjects(Long teacherId, SubjectEntityClass subjectEntityClass){
        return teacherDao.updateTeachersSubjects(teacherId, subjectEntityClass);
    }
}
