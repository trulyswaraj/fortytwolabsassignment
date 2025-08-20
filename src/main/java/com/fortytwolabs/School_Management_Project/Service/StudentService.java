package com.fortytwolabs.School_Management_Project.Service;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.StudentDao;
import jakarta.transaction.SystemException;

import java.util.List;


public class StudentService {

    private StudentDao studentDao;

    public StudentService(){
        this.studentDao = new StudentDao();
    }

    public StudentEntityClass addStudent(StudentEntityClass studentEntityClass) throws SystemException {
        try{
            studentDao.save(studentEntityClass);
        } catch (Exception e){
            throw new SystemException("Error While Adding student : "+ studentEntityClass.getName());
        }
        return studentEntityClass;
    }

    public StudentEntityClass getStudentById(Long id){
        return studentDao.getById(id);
    }

    public List<StudentEntityClass> getAllStudents(){
        return studentDao.getAllStudents();
    }

    public StudentEntityClass updateStudent(StudentEntityClass studentEntityClass){
        return studentDao.update(studentEntityClass);
    }

    public void deleteStudent(Long id){
        studentDao.delete(id);
    }

}
