package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public TeacherEntityClass getById(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(TeacherEntityClass.class , id);
        }
    }

    public List<TeacherEntityClass> getAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("from TeacherEntityClass", TeacherEntityClass.class).list();
        }
    }

    public void updateTeacher(TeacherEntityClass teacherEntityClass){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(teacherEntityClass);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Long id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            TeacherEntityClass teacherEntityClass = session.get(TeacherEntityClass.class, id);
            if(teacherEntityClass != null){
                session.remove(teacherEntityClass);
            }
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public TeacherEntityClass assignTeacherToSubject(Long teacherId, SubjectEntityClass subjectEntityClass){
        Transaction transaction = null;
        TeacherEntityClass teacherEntityClass = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            teacherEntityClass = session.get(TeacherEntityClass.class, teacherId);

            if(teacherEntityClass == null) throw new RuntimeException("Teacher Not Found!");

            teacherEntityClass.getSubjects().add(subjectEntityClass);
            subjectEntityClass.getTeachers().add(teacherEntityClass);

            session.merge(teacherEntityClass);
            session.merge(subjectEntityClass);

            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return teacherEntityClass;
    }

    public TeacherEntityClass updateTeachersSubjects(Long teacherId, SubjectEntityClass subjectEntityClass){
        Transaction transaction = null;
        TeacherEntityClass teacherEntityClass = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            teacherEntityClass = session.get(TeacherEntityClass.class, teacherId);
            if(teacherEntityClass == null) throw new RuntimeException("Teacher with given id not found");

            for(SubjectEntityClass oldSubjects : teacherEntityClass.getSubjects()){
                oldSubjects.getTeachers().remove(teacherEntityClass);
                session.merge(oldSubjects);
            }
            teacherEntityClass.getSubjects().clear();

            teacherEntityClass.getSubjects().add(subjectEntityClass);
            subjectEntityClass.getTeachers().add(teacherEntityClass);

            session.merge(teacherEntityClass);
            session.merge(subjectEntityClass);

            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return teacherEntityClass;
    }

    public TeacherEntityClass assignTeacherToStudent(Long teacherId, StudentEntityClass studentEntityClass){
        Transaction transaction = null;
        TeacherEntityClass teacherEntityClass = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            teacherEntityClass = session.get(TeacherEntityClass.class, teacherId);
            if(teacherEntityClass == null){
                throw new RuntimeException("Teacher With given Id not found!");
            }

            teacherEntityClass.getStudents().add(studentEntityClass);
            studentEntityClass.getTeachers().add(teacherEntityClass);

            session.merge(teacherEntityClass);
            session.merge(studentEntityClass);

            transaction.commit();
        } catch(Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return teacherEntityClass;
    }

}
