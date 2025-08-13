package com.fortytwolabs.School_Management_Project.Service;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.StudentRepository;
import com.fortytwolabs.School_Management_Project.Repository.SubjectRepository;
import com.fortytwolabs.School_Management_Project.Repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }


    public List<TeacherEntityClass> getAllTeachers(){
        return teacherRepository.findAll();
    }

    public TeacherEntityClass save(TeacherEntityClass teacherEntityClass) {
        return teacherRepository.save(teacherEntityClass);
    }

    public TeacherEntityClass updateTeacher(Integer id, TeacherEntityClass updatedTeacher){
        TeacherEntityClass existingTeacher = teacherRepository.findById(id).get();

        existingTeacher.setTeacherName(updatedTeacher.getTeacherName());
        existingTeacher.setTeacherEmail(updatedTeacher.getTeacherEmail());

        return teacherRepository.save(existingTeacher);
    }

    public List<TeacherEntityClass> findByTeacherEmail(String email){
        return teacherRepository.findByTeacherEmail(email);
    }

    public List<TeacherEntityClass> findByTeacherName(String name){
        return teacherRepository.findByTeacherName(name);
    }

    public Optional<TeacherEntityClass> findTeacherById(Integer id){
        return teacherRepository.findById(id);
    }

    public TeacherEntityClass assignTeacherToSubject(@PathVariable Long teacherId, @RequestParam Long subjectId ){
        TeacherEntityClass teacherEntityClass = teacherRepository.findByTeacherId(teacherId);
        if(teacherEntityClass == null){
            throw new RuntimeException("Teacher With Given Id Not Found!");
        }
        SubjectEntityClass subjectEntityClass = subjectRepository.findSubjectById(subjectId);
        if(subjectEntityClass == null){
            throw new RuntimeException("Subject With Given Id Not Found!");
        }

        teacherEntityClass.getSubjects().add(subjectEntityClass);
        subjectEntityClass.getTeachers().add(teacherEntityClass);

        return teacherRepository.save(teacherEntityClass);
    }


    public TeacherEntityClass assignTeacherToStudent(@PathVariable Long teacherId, @RequestParam Long studentId){
        TeacherEntityClass teacherEntityClass = teacherRepository.findByTeacherId(teacherId);
        if(teacherEntityClass == null){
            throw new RuntimeException("Teacher with Given Id Not Found!");
        }
        StudentEntityClass studentEntityClass = studentRepository.findStudentById(studentId).orElseThrow(() -> new RuntimeException("Student with the given Id Not Found."));
        teacherEntityClass.getStudents().add(studentEntityClass);
        studentEntityClass.getTeachers().add(teacherEntityClass);

        return teacherRepository.save(teacherEntityClass);
    }

    public TeacherEntityClass updateTeacherSubjects(Long teacherId, Long subjectIds){
        TeacherEntityClass teacher = teacherRepository.findByTeacherId(teacherId);
        if(teacher == null){
            throw new RuntimeException("Teacher with given api not found.");
        }
        SubjectEntityClass subject = subjectRepository.findSubjectById(subjectIds);
        if(subject == null){
            throw new RuntimeException("Subject with given ids not found!");
        }
        teacher.getSubjects().clear();
        teacher.getSubjects().add(subject);
        subject.getTeachers().add(teacher);

        return teacherRepository.save(teacher);
    }

}
