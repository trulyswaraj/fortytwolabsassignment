package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.util.HibernateUtil;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;

public class TeacherDao {

    public void save(TeacherEntityClass teacherEntityClass){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            session.persist(teacherEntityClass);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

//    public TeacherEntityClass getById(Long id){
//        try(Session session = HibernateUtil.getSessionFactory().openSession()){
//            return session.get(TeacherEntityClass.class , id);
//        }
//    }

    public TeacherEntityClass getByIdUsingCriteria(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TeacherEntityClass> cq = cb.createQuery(TeacherEntityClass.class);
            Root<TeacherEntityClass> root = cq.from(TeacherEntityClass.class);
            root.fetch("students", JoinType.LEFT);
            root.fetch("subjects", JoinType.LEFT);

            cq.select(root).where(cb.equal(root.get("id"), id));

            return session.createQuery(cq).uniqueResult();

        }
    }

//    public List<TeacherEntityClass> getAll(){
//        try(Session session = HibernateUtil.getSessionFactory().openSession()){
//            return session.createQuery("from TeacherEntityClass", TeacherEntityClass.class).list();
//        }
//    }

    public List<TeacherEntityClass> getAllUsingCriteria(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TeacherEntityClass> cq = cb.createQuery(TeacherEntityClass.class);
            Root<TeacherEntityClass> root = cq.from(TeacherEntityClass.class);
            root.fetch("students", JoinType.LEFT);
            root.fetch("subjects", JoinType.LEFT);
            cq.select(root).distinct(true);

            List<TeacherEntityClass> teachers = session.createQuery(cq).getResultList();

            return teachers;

        }
    }
//
//    public void updateTeacher(TeacherEntityClass teacherEntityClass){
//        Transaction transaction = null;
//        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.merge(teacherEntityClass);
//            transaction.commit();
//        } catch (Exception e){
//            if(transaction != null) transaction.rollback();
//            e.printStackTrace();
//        }
//    }

    public void updateTeacherUsingCriteria(TeacherEntityClass teacherEntityClass){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<TeacherEntityClass> update = cb.createCriteriaUpdate(TeacherEntityClass.class);
            Root<TeacherEntityClass> root = update.from(TeacherEntityClass.class);

            update.set("teacherName", teacherEntityClass.getTeacherName());
            update.set("teacherEmail", teacherEntityClass.getTeacherEmail());

            update.where(cb.equal(root.get("id"), teacherEntityClass.getTeacherId()));

            session.createQuery(update).executeUpdate();
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error Updating Teacher.");
        }
    }

//    public void delete(Long id){
//        Transaction transaction = null;
//        try(Session session = HibernateUtil.getSessionFactory().openSession()){
//            transaction = session.beginTransaction();
//            TeacherEntityClass teacherEntityClass = session.get(TeacherEntityClass.class, id);
//            if(teacherEntityClass != null){
//                session.remove(teacherEntityClass);
//            }
//            transaction.commit();
//        } catch (Exception e){
//            if(transaction != null) transaction.rollback();
//            e.printStackTrace();
//        }
//    }

    public void deleteUsingCriteria(Long id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<TeacherEntityClass> delete = cb.createCriteriaDelete(TeacherEntityClass.class);
            Root<TeacherEntityClass> root = delete.from(TeacherEntityClass.class);

            delete.where(cb.equal(root.get("id"), id));

            session.createQuery(delete).executeUpdate();
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error Deleting Teacher.");
        }
    }
//
//    public TeacherEntityClass assignTeacherToSubject(Long teacherId, SubjectEntityClass subjectEntityClass){
//        Transaction transaction = null;
//        TeacherEntityClass teacherEntityClass = null;
//
//        try(Session session = HibernateUtil.getSessionFactory().openSession()){
//            transaction = session.beginTransaction();
//
//            teacherEntityClass = session.get(TeacherEntityClass.class, teacherId);
//
//            if(teacherEntityClass == null) throw new RuntimeException("Teacher Not Found!");
//
//            teacherEntityClass.getSubjects().add(subjectEntityClass);
//            subjectEntityClass.getTeachers().add(teacherEntityClass);
//
//            session.merge(teacherEntityClass);
//            session.merge(subjectEntityClass);
//
//            transaction.commit();
//        } catch (Exception e){
//            if(transaction != null) transaction.rollback();
//            e.printStackTrace();
//        }
//        return teacherEntityClass;
//    }

    public TeacherEntityClass assignTeacherToSubjectUsingCriteria(Long teacherId, Long subjectId ){
        Transaction transaction = null;
        TeacherEntityClass teacherEntityClass = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TeacherEntityClass> cq = cb.createQuery(TeacherEntityClass.class);

            Root<TeacherEntityClass> teacherRoot = cq.from(TeacherEntityClass.class);
            teacherRoot.fetch("subjects", JoinType.LEFT);
            cq.select(teacherRoot).where(cb.equal(teacherRoot.get("id"), teacherId)).distinct(true);
            teacherEntityClass = session.createQuery(cq).uniqueResult();

            if(teacherEntityClass == null){
                throw new RuntimeException("Teacher Not Found!");
            }

            SubjectEntityClass subjectEntityClass = session.get(SubjectEntityClass.class, subjectId);
            if(subjectEntityClass == null){
                throw new RuntimeException("Subject Not Found");
            }

            if(teacherEntityClass.getSubjects() == null) teacherEntityClass.setSubjects(new HashSet<>());
            //if(subjectEntityClass.getTeachers() == null) subjectEntityClass.setTeachers(new HashSet<>());

            teacherEntityClass.getSubjects().add(subjectEntityClass);
            //subjectEntityClass.getTeachers().add(teacherEntityClass);

           // SubjectEntityClass managedSubject = session.merge(subjectId);


//            teacherEntityClass.getSubjects().add(subjectEntityClass);
//            subjectEntityClass.getTeachers().add(teacherEntityClass);

            session.merge(teacherEntityClass);
           // session.merge(subjectEntityClass);

            transaction.commit();
       } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return teacherEntityClass;
    }

//    public TeacherEntityClass updateTeachersSubjects(Long teacherId, SubjectEntityClass subjectEntityClass){
//        Transaction transaction = null;
//        TeacherEntityClass teacherEntityClass = null;
//
//        try(Session session = HibernateUtil.getSessionFactory().openSession()){
//            transaction = session.beginTransaction();
//            teacherEntityClass = session.get(TeacherEntityClass.class, teacherId);
//            if(teacherEntityClass == null) throw new RuntimeException("Teacher with given id not found");
//
//            for(SubjectEntityClass oldSubjects : teacherEntityClass.getSubjects()){
//                oldSubjects.getTeachers().remove(teacherEntityClass);
//                session.merge(oldSubjects);
//            }
//            teacherEntityClass.getSubjects().clear();
//
//            teacherEntityClass.getSubjects().add(subjectEntityClass);
//            subjectEntityClass.getTeachers().add(teacherEntityClass);
//
//            session.merge(teacherEntityClass);
//            session.merge(subjectEntityClass);
//
//            transaction.commit();
//        } catch (Exception e){
//            if(transaction != null) transaction.rollback();
//            e.printStackTrace();
//        }
//        return teacherEntityClass;
//    }

    public TeacherEntityClass updateTeacherSubjectsUsingCriteria(Long teacherid, SubjectEntityClass subjectEntityClass){
        Transaction transaction = null;
        TeacherEntityClass teacherEntityClass = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TeacherEntityClass> cq = cb.createQuery(TeacherEntityClass.class);
            Root<TeacherEntityClass> teacherRoot = cq.from(TeacherEntityClass.class);
            teacherRoot.join("subjects", JoinType.LEFT);

            cq.select(teacherRoot).where(cb.equal(teacherRoot.get("id"), teacherid )).distinct(true);

            teacherEntityClass = session.createQuery(cq).uniqueResult();

            if(teacherEntityClass == null){
                throw new RuntimeException("Teacher With Given Id Not Found!");
            }

            for (SubjectEntityClass oldSubject : teacherEntityClass.getSubjects()){
                oldSubject.getTeachers().remove(teacherEntityClass);
                session.merge(oldSubject);
            }
            teacherEntityClass.getSubjects().clear();

            SubjectEntityClass managedSubject = session.merge(subjectEntityClass);

            teacherEntityClass.getSubjects().add(managedSubject);
            managedSubject.getTeachers().add(teacherEntityClass);

            teacherEntityClass = session.merge(teacherEntityClass);

            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return teacherEntityClass;
    }


//    public TeacherEntityClass assignTeacherToStudent(Long teacherId, StudentEntityClass studentEntityClass) {
//        Transaction transaction = null;
//        TeacherEntityClass teacherEntityClass = null;
//
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            teacherEntityClass = session.get(TeacherEntityClass.class, teacherId);
//            if (teacherEntityClass == null) {
//                throw new RuntimeException("Teacher with given Id not found!");
//            }
//
//            // Add the student to teacher and vice versa
//            teacherEntityClass.getStudents().add(studentEntityClass);
//            studentEntityClass.getTeachers().add(teacherEntityClass);
//
//            session.merge(teacherEntityClass);
//            session.merge(studentEntityClass);
//
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            e.printStackTrace();
//        }
//        return teacherEntityClass;
//    }

    public TeacherEntityClass assignTeacherToStudentUsingCriteria(Long teacherId, StudentEntityClass studentEntityClass){
        Transaction transaction = null;
        TeacherEntityClass teacherEntityClass = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction=session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TeacherEntityClass> cq = cb.createQuery(TeacherEntityClass.class);

            Root<TeacherEntityClass> teacherRoot = cq.from(TeacherEntityClass.class);

            teacherRoot.join("students", JoinType.LEFT);

            cq.select(teacherRoot).where(cb.equal(teacherRoot.get("id"),teacherId)).distinct(true);

            teacherEntityClass = session.createQuery(cq).uniqueResult();

            if (teacherEntityClass == null){
                throw  new RuntimeException("Teacher With Given Id Not Found!");
            }

            StudentEntityClass managedStudent = session.merge(studentEntityClass);

            teacherEntityClass.getStudents().add(managedStudent);
            managedStudent.getTeachers().add(teacherEntityClass);

            teacherEntityClass=session.merge(teacherEntityClass);

//            teacherEntityClass.getStudents().add(studentEntityClass);
//            studentEntityClass.getTeachers().add(teacherEntityClass);
//
//            session.merge(teacherEntityClass);
//            session.merge(studentEntityClass);

            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return teacherEntityClass;
    }

}
