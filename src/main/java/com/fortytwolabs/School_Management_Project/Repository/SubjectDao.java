package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.util.HibernateUtil;
import jakarta.persistence.criteria.*;
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

    public SubjectEntityClass getByIdUsingCriteria(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<SubjectEntityClass> cq = cb.createQuery(SubjectEntityClass.class);
            Root<SubjectEntityClass> root = cq.from(SubjectEntityClass.class);
            cq.select(root).where(cb.equal(root.get("id"), id));
            return session.get(SubjectEntityClass.class, id);
        }
    }

    public List<SubjectEntityClass> getAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("from SubjectEntityClass", SubjectEntityClass.class).list();
        }
    }

    public List<SubjectEntityClass> getAllUsingCriteria(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<SubjectEntityClass> cq = cb.createQuery(SubjectEntityClass.class);
            Root<SubjectEntityClass> root = cq.from(SubjectEntityClass.class);
            cq.select(root);
            return session.createQuery(cq).getResultList();
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

        public void updateSubjectUsingCriteria(SubjectEntityClass subjectEntityClass){
            Transaction transaction = null;
            try(Session session = HibernateUtil.getSessionFactory().openSession()){
                transaction=session.beginTransaction();

                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaUpdate<SubjectEntityClass> update = cb.createCriteriaUpdate(SubjectEntityClass.class);
                Root<SubjectEntityClass> root = update.from(SubjectEntityClass.class);

                update.set("name", subjectEntityClass.getName());

                update.where(cb.equal(root.get("id"), subjectEntityClass.getId()));

                session.createQuery(update).executeUpdate();

                transaction.commit();
            } catch (Exception e){
                if(transaction != null) transaction.rollback();
                e.printStackTrace();
                throw new RuntimeException("Error Updating Subject.");
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

    public void deleteSubjectUsingCriteria(Long id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<SubjectEntityClass> delete = cb.createCriteriaDelete(SubjectEntityClass.class);
            Root<SubjectEntityClass> root = delete.from(SubjectEntityClass.class);

            delete.where(cb.equal(root.get("id"), id));

            session.createQuery(delete).executeUpdate();
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

}
