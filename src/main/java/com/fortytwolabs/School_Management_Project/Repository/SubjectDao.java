package com.fortytwolabs.School_Management_Project.Repository;

import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import dev.morphia.query.updates.UpdateOperators;


import java.util.List;

public class SubjectDao {

    private final Datastore datastore;

    public SubjectDao(Datastore datastore){
        this.datastore = datastore;
    }

    public void save(SubjectEntityClass subjectEntityClass){
        subjectEntityClass.generateId(datastore);
        datastore.save(subjectEntityClass);
    }

    public SubjectEntityClass getById(Long id){
        return datastore.find(SubjectEntityClass.class)
                .filter(Filters.eq("_id", id))
                .first();
    }

    public List<SubjectEntityClass> getAll(){
       return datastore.find(SubjectEntityClass.class).iterator().toList();
    }

    public void updateSubject(SubjectEntityClass subjectEntityClass){
        datastore.find(SubjectEntityClass.class)
                .filter(Filters.eq("_id", subjectEntityClass.getId()))
                .update(UpdateOperators.set("name", subjectEntityClass.getName()))
                .execute();

    }

    public void deleteSubject(Long id){
        datastore.find(SubjectEntityClass.class)
                .filter(Filters.eq("_id", id))
                .delete();
    }

    public SubjectEntityClass assignTeacherToSubject(Long subjectId, TeacherEntityClass teacher) {
        SubjectEntityClass subject = getById(subjectId);
        if (subject == null) return null;

        subject.getTeachers().add(teacher);
        teacher.getSubjects().add(subject);

        datastore.save(subject);
        datastore.save(teacher);

        return subject;
    }


}
