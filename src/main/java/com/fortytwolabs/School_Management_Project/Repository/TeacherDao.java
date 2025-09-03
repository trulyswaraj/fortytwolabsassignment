package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.util.CounterUtil;
import com.fortytwolabs.School_Management_Project.util.MongoDBUtil;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import dev.morphia.query.updates.UpdateOperators;
import java.util.HashSet;
import java.util.List;

public class TeacherDao {
    private final Datastore datastore;
    public TeacherDao(Datastore datastore){
        this.datastore = MongoDBUtil.getDatastore();
    }

   public void save(TeacherEntityClass teacherEntityClass){
        if(teacherEntityClass.getTeacherId() == null){
            Long nextId = CounterUtil.getNextSequence("teacherId", datastore);
            teacherEntityClass.setTeacherId(nextId);
        }

        datastore.save(teacherEntityClass);
   }

   public TeacherEntityClass getById(Long id){
        return datastore.find(TeacherEntityClass.class)
                .filter(Filters.eq("_id", id))
                .first();
   }

   public List<TeacherEntityClass> getAll(){
        return datastore.find(TeacherEntityClass.class).iterator().toList();
   }

    public void updateTeacher(TeacherEntityClass teacherEntityClass){
        datastore.find(TeacherEntityClass.class)
                .filter(Filters.eq("_id", teacherEntityClass.getTeacherId()))
                .update(UpdateOperators.set("teacherName", teacherEntityClass.getTeacherName()),
                        UpdateOperators.set("teacherEmail", teacherEntityClass.getTeacherEmail()))
                .execute();
    }

    public void deleteTeacher(Long id){
        datastore.find(TeacherEntityClass.class)
                .filter(Filters.eq("_id", id))
                .delete();
    }

    public TeacherEntityClass assignTeacherToSubject(Long teacherId, SubjectEntityClass subjectEntityClass){
        TeacherEntityClass teacherEntityClass = getById(teacherId);
        if(teacherEntityClass == null) throw new RuntimeException("Teacher Not Found!");
        if(teacherEntityClass.getSubjects() == null) teacherEntityClass.setSubjects(new HashSet<>());
        teacherEntityClass.getSubjects().add(subjectEntityClass);
        if(subjectEntityClass.getTeachers() == null)
            subjectEntityClass.setTeachers(new HashSet<>());
        subjectEntityClass.getTeachers().add(teacherEntityClass);
        datastore.save(teacherEntityClass);
        datastore.save(subjectEntityClass);
        return teacherEntityClass;
    }

    public TeacherEntityClass assignTeacherToStudent(Long teacherId, StudentEntityClass studentEntityClass){
        TeacherEntityClass teacherEntityClass = getById(teacherId);
        if(teacherEntityClass == null) throw new RuntimeException("Teacher Not Found!");
        if(teacherEntityClass.getStudents() == null) teacherEntityClass.setStudents(new HashSet<>());
        teacherEntityClass.getStudents().add(studentEntityClass);
        if(studentEntityClass.getTeachers() == null)
            studentEntityClass.setTeachers(new HashSet<>());
        studentEntityClass.getTeachers().add(teacherEntityClass);
        datastore.save(teacherEntityClass);
        datastore.save(studentEntityClass);
        return teacherEntityClass;
    }

    public TeacherEntityClass updateTeacherSubjects(Long teacherId, SubjectEntityClass subjectEntityClass){
        TeacherEntityClass teacherEntityClass = getById(teacherId);
        if(teacherEntityClass == null) throw new RuntimeException("Teacher Not Found!");
        teacherEntityClass.setSubjects(new HashSet<>());
        teacherEntityClass.getSubjects().add(subjectEntityClass);
        datastore.save(teacherEntityClass);
        return teacherEntityClass;
    }

}
