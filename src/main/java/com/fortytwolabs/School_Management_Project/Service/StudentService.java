package com.fortytwolabs.School_Management_Project.Service;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.StudentDao;
import com.fortytwolabs.School_Management_Project.Repository.SubjectDao;
import com.fortytwolabs.School_Management_Project.Repository.TeacherDao;
import com.fortytwolabs.School_Management_Project.util.MongoDBUtil;
import dev.morphia.Datastore;

import java.util.List;

public class StudentService {

    private StudentDao studentDao;
    private TeacherDao teacherDao;
    private SubjectDao subjectDao;
    public StudentService(){
        Datastore datastore = MongoDBUtil.getDatastore();
        this.studentDao = new StudentDao(datastore);
        this.teacherDao= new TeacherDao(datastore);
        this.subjectDao = new SubjectDao(datastore);
    }

    public StudentEntityClass addStudent(StudentEntityClass studentEntityClass) {
        try{
            studentDao.save(studentEntityClass);
        } catch (Exception e){
            throw new RuntimeException("Error While Adding student : "+ studentEntityClass.getName());
        }
        return studentEntityClass;
    }

    public List<StudentEntityClass> getAllStudents(int pageNumber, int pageSize){
        return studentDao.getAllStudents(pageNumber, pageSize);
    }

    public StudentEntityClass getStudentById(Long id){
        return studentDao.getById(id);
    }

    public long getTotalStudents(){
        return studentDao.getTotalStudents();
    }

    public StudentEntityClass updateStudent(Long id, StudentEntityClass studentEntityClass){
        return studentDao.updateStudent(id, studentEntityClass);
    }

    public void deleteStudent(Long id){
        studentDao.delete(id);
    }

    public StudentEntityClass assignTeacherToStudent(Long studentId, Long teacherId) {
        StudentEntityClass student = studentDao.getById(studentId);
        TeacherEntityClass teacher = teacherDao.getById(teacherId);

        if (student == null || teacher == null) {
            return null;
        }

        return studentDao.assignTeacherToStudent(studentId, teacher);
    }


    public StudentEntityClass assignSubjectToStudent(Long studentId, Long subjectId) {
        StudentEntityClass student = studentDao.getById(studentId);
        SubjectEntityClass subject = subjectDao.getById(subjectId);

        if (student == null || subject == null) return null;

        return studentDao.assignSubjectToStudent(studentId, subject);
    }

}
