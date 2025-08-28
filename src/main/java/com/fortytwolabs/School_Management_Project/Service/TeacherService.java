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
        return teacherDao.getByIdUsingCriteria(id);
    }

    public TeacherEntityClass getByIdUsingCriteria(Long id){
        return teacherDao.getByIdUsingCriteria(id);
    }

    public List<TeacherEntityClass> getAllTeachers(){
        return teacherDao.getAllUsingCriteria();
    }

    public List<TeacherEntityClass> getTeacherUsingCriteria(){ return teacherDao.getAllUsingCriteria();}

    public void updateTeacher(TeacherEntityClass teacherEntityClass){
        teacherDao.updateTeacherUsingCriteria(teacherEntityClass);
    }

    public void updateTeacherUsingCriteria(TeacherEntityClass teacherEntityClass){teacherDao.updateTeacherUsingCriteria(teacherEntityClass);}

    public void deleteTeacher(Long id){
        teacherDao.deleteUsingCriteria(id);
    }

    public void deleteTeacherUsingCriteria(Long id){teacherDao.deleteUsingCriteria(id);}

    public SubjectEntityClass getSubjectById(Long id) {
        return subjectDao.getById(id);
    }

    public StudentEntityClass getStudentById(Long id) {
        return studentDao.getById(id);
    }


    public TeacherEntityClass assignTeacherToSubject(Long teacherId, SubjectEntityClass subjectEntityClass){
        return teacherDao.assignTeacherToSubjectUsingCriteria(teacherId, subjectEntityClass);
    }

    public TeacherEntityClass assignTeacherToStudent(Long teacherId, Long studentId) {
        // Fetch the student entity first
        StudentEntityClass student = studentDao.getById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }
        return teacherDao.assignTeacherToStudentUsingCriteria(teacherId, student);
    }


    public TeacherEntityClass updateTeacherSubjects(Long teacherId, SubjectEntityClass subjectEntityClass){
        return teacherDao.updateTeacherSubjectsUsingCriteria(teacherId, subjectEntityClass);
    }
}
