package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SubjectDao {

    public void save(SubjectEntityClass subjectEntityClass){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
          transaction = session.beginTransaction();
            session.persist(subjectEntityClass);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public SubjectEntityClass getById(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(SubjectEntityClass.class, id);
        }
    }

    public List<SubjectEntityClass> getAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("from SubjectEntityClass", SubjectEntityClass.class).list();
        }
    }

    public void updateSubject(SubjectEntityClass subjectEntityClass){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(subjectEntityClass);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteSubject(Long id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            SubjectEntityClass subjectEntityClass = session.get(SubjectEntityClass.class, id);
            if(subjectEntityClass != null){
                session.remove(subjectEntityClass);
            }
            transaction.commit();
        } catch (Exception ex){
            if(transaction != null) transaction.rollback();
            ex.printStackTrace();
        }
    }

}
