package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.util.HibernateUtil;
import jakarta.persistence.criteria.*;
import jakarta.transaction.SystemException;
import org.hibernate.Hibernate;
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

//    public List<StudentEntityClass> getAllStudentCriteria(int pageNumber, int pageSize){
//        try(Session session = HibernateUtil.getSessionFactory().openSession()){
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<StudentEntityClass> cq = cb.createQuery(StudentEntityClass.class);
//            Root<StudentEntityClass> root = cq.from(StudentEntityClass.class);
//            root.fetch("teachers", JoinType.LEFT);
//            root.fetch("subjects", JoinType.LEFT);
//            cq.select(root).distinct(true);
//
//            int offset = (pageNumber -1 )*pageSize;
//            List<StudentEntityClass> students = session.createQuery(cq)
//                    .setFirstResult(offset)
//                    .setMaxResults(pageSize)
//                    .getResultList();
//
//            return students;
//           // return session.createQuery(cq).getResultList();
//        }
//    }

    public List<StudentEntityClass> getAllStudentCriteria(int page, int size) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<StudentEntityClass> students = session.createQuery("from StudentEntityClass", StudentEntityClass.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();

            // Initialize lazy collections
            for (StudentEntityClass s : students) {
                Hibernate.initialize(s.getTeachers());
                Hibernate.initialize(s.getSubjects());
                // Also initialize each teacher's students if needed
                for (TeacherEntityClass t : s.getTeachers()) {
                    Hibernate.initialize(t.getStudents());
                }
            }

            return students;
        }
    }


    public long getTotalStudents(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            cq.select(cb.count(cq.from(StudentEntityClass.class)));
            return session.createQuery(cq).getSingleResult();
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
