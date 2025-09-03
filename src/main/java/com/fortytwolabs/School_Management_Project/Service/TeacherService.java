package com.fortytwolabs.School_Management_Project.Service;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.*;
import com.fortytwolabs.School_Management_Project.util.MongoDBUtil;
import dev.morphia.Datastore;

import javax.xml.crypto.Data;
import java.util.List;

public class TeacherService {

    private TeacherDao teacherDao;
    private SubjectDao subjectDao;
    private StudentDao studentDao;

    public TeacherService(){
        Datastore datastore = MongoDBUtil.getDatastore();
        this.teacherDao = new TeacherDao(datastore);
        this.subjectDao= new SubjectDao(datastore);
        this.studentDao= new StudentDao(datastore);
    }

    public void addTeacher(TeacherEntityClass teacher) {
        teacherDao.save(teacher);
    }

    public TeacherEntityClass getTeacherById(Long id) {
        return teacherDao.getById(id);
    }

    public List<TeacherEntityClass> getAllTeachers() {
        return teacherDao.getAll();
    }

    public void updateTeacher(TeacherEntityClass teacher) {
        teacherDao.updateTeacher(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherDao.deleteTeacher(id);
    }

    public SubjectEntityClass getSubjectById(Long id) {
        return subjectDao.getById(id);
    }

    public StudentEntityClass getStudentById(Long id) {
        return studentDao.getById(id);
    }

    public TeacherEntityClass assignTeacherToSubject(Long teacherId, Long subjectId) {
        SubjectEntityClass subject = subjectDao.getById(subjectId);
        if (subject == null) throw new RuntimeException("Subject not found with id: " + subjectId);
        return teacherDao.assignTeacherToSubject(teacherId, subject);
    }

    public TeacherEntityClass assignTeacherToStudent(Long teacherId, Long studentId) {
        StudentEntityClass student = studentDao.getById(studentId);
        if (student == null) throw new RuntimeException("Student not found with id: " + studentId);
        return teacherDao.assignTeacherToStudent(teacherId, student);
    }

    public TeacherEntityClass updateTeacherSubjects(Long teacherId, Long subjectId) {
        SubjectEntityClass subject = subjectDao.getById(subjectId);
        if (subject == null) throw new RuntimeException("Subject not found with id: " + subjectId);
        return teacherDao.updateTeacherSubjects(teacherId, subject);
    }
}
