package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.util.HibernateUtil;
import jakarta.persistence.criteria.*;
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

    public StudentEntityClass getByIdUsingCriteria(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cd = session.getCriteriaBuilder();
            CriteriaQuery<StudentEntityClass> cq = cd.createQuery(StudentEntityClass.class);
            Root<StudentEntityClass> root = cq.from(StudentEntityClass.class);

            cq.select(root).where(cd.equal(root.get("id"), id));

            return session.createQuery(cq).uniqueResultOptional().orElse(null);
        }
    }


    public List<StudentEntityClass> getAllStudents(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("from StudentEntityClass", StudentEntityClass.class).list();
        }
    }

    public List<StudentEntityClass> getAllStudentCriteria(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<StudentEntityClass> cq = cb.createQuery(StudentEntityClass.class);
            Root<StudentEntityClass> root = cq.from(StudentEntityClass.class);

            cq.select(root);
            return session.createQuery(cq).getResultList();
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

    public StudentEntityClass updateStudentUsingCriteria(Long id, StudentEntityClass studentEntityClass){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<StudentEntityClass> update = cb.createCriteriaUpdate(StudentEntityClass.class);
            Root<StudentEntityClass> root = update.from(StudentEntityClass.class);

            update.set("name", studentEntityClass.getName());
            update.set("email",studentEntityClass.getEmail());

            update.where(cb.equal(root.get("id"), id));

            int rowsUpdated = session.createQuery(update).executeUpdate();
            transaction.commit();

            if(rowsUpdated >0){
                studentEntityClass.setId(id);
                return studentEntityClass;
            } else{
                return null;
            }

        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
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

    public void deleteUsingCriteria(Long id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<StudentEntityClass> delete = cb.createCriteriaDelete(StudentEntityClass.class);

            Root<StudentEntityClass> root = delete.from(StudentEntityClass.class);

            delete.where(cb.equal(root.get("id"), id));

            session.createQuery(delete).executeUpdate();

            transaction.commit();
        } catch(Exception e){
            if(transaction != null){
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

}
