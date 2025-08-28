package com.fortytwolabs.School_Management_Project.Service;

import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.SubjectDao;

import java.util.List;

public class SubjectService {
    private SubjectDao subjectDao;

    public SubjectService(){
        this.subjectDao= new SubjectDao();
    }

    public SubjectEntityClass save(SubjectEntityClass subjectEntityClass){
        subjectDao.save(subjectEntityClass);
        return subjectEntityClass;
    }

    public void addSubject(SubjectEntityClass subjectEntityClass){
        subjectDao.save(subjectEntityClass);
    }
    public SubjectEntityClass getSubjectById(Long id){
        return subjectDao.getById(id);
    }
    public SubjectEntityClass getByIdUsingCriteria(Long id){return subjectDao.getByIdUsingCriteria(id);}
    public List<SubjectEntityClass> getAllSubjects(){
        return subjectDao.getAll();
    }
    public List<SubjectEntityClass> getAllUsingCriteria(){return subjectDao.getAllUsingCriteria();}
    public void updateSubject(SubjectEntityClass subjectEntityClass){
        subjectDao.updateSubject(subjectEntityClass);
    }
    public void updateSubjectUsingCriteria(SubjectEntityClass subjectEntityClass){
        SubjectEntityClass subjectEntityClass1 = getByIdUsingCriteria(subjectEntityClass.getId());
        subjectEntityClass1.setName(subjectEntityClass.getName());
        subjectDao.updateSubjectUsingCriteria(subjectEntityClass);}
    public void deleteSubject(Long id){
        subjectDao.deleteSubjectUsingCriteria(id);
    }
}
