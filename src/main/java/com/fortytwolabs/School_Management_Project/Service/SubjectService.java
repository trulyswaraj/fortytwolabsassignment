package com.fortytwolabs.School_Management_Project.Service;

import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.SubjectDao;
import com.fortytwolabs.School_Management_Project.util.MongoDBUtil;
import dev.morphia.Datastore;

import java.util.List;

public class SubjectService {
    private SubjectDao subjectDao;

    public SubjectService(){
        this.subjectDao= new SubjectDao(MongoDBUtil.getDatastore());
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
    public List<SubjectEntityClass> getAllSubjects(){
        return subjectDao.getAll();
    }
    public void updateSubject(SubjectEntityClass subjectEntityClass){
        subjectDao.updateSubject(subjectEntityClass);}
    public void deleteSubject(Long id){
        subjectDao.deleteSubject(id);
    }
}
