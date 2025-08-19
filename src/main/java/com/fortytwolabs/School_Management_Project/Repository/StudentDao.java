package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.util.HibernateUtil;
import jakarta.transaction.SystemException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDao {
    public void save(StudentEntityClass studentEntityClass) throws SystemException {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(studentEntityClass);
            transaction.commit();
        } catch (Exception ex){
            if (transaction != null) transaction.rollback();
            ex.printStackTrace();
        }

    }

    public StudentEntityClass getById(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(StudentEntityClass.class, id);
        }
    }

    public List<StudentEntityClass> getAllStudents(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("from StudentEntityClass", StudentEntityClass.class).list();
        }
    }

    public StudentEntityClass update(StudentEntityClass studentEntityClass){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(studentEntityClass);
            transaction.commit();
        } catch (Exception ex){
            if(transaction != null) transaction.rollback();;
            ex.printStackTrace();
        }
        return studentEntityClass;
    }

    public void delete(Long id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            StudentEntityClass studentEntityClass = session.get(StudentEntityClass.class, id);
            if(studentEntityClass != null){
                session.remove(studentEntityClass);
            }
            transaction.commit();
        } catch (Exception ex){
            if(transaction != null) transaction.rollback();
            ex.printStackTrace();
        }
    }

}
