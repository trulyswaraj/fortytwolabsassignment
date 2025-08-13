package com.fortytwolabs.School_Management_Project.Service;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Repository.StudentRepository;
import com.fortytwolabs.School_Management_Project.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    public StudentService(StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<StudentEntityClass> getAllStudents(){
        return studentRepository.findAll();
    }

    public StudentEntityClass saveStudent(StudentEntityClass studentEntityClass) {
        return studentRepository.save(studentEntityClass);
    }

    public StudentEntityClass updateStudent(Long id, StudentEntityClass updatedStudent){
        StudentEntityClass existingStudent = studentRepository.findById(id).get();

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());

        return studentRepository.save(existingStudent);
    }

    public String deleteStudentById(Long id){
        studentRepository.deleteById(id);
        return "Student Deleted Successfully!";
    }

    public StudentEntityClass getStudentById(Long id){
        Optional<StudentEntityClass> studentEntityClassOptional = studentRepository.findById(id);
        if(studentEntityClassOptional.isPresent()){
            return studentEntityClassOptional.get();
        } else{
            throw new RuntimeException("Student Not Found with Id : " + id);
        }
    }

    public StudentEntityClass addSubjectsToStudent(Long studentId, List<Long> subjectIds){
        StudentEntityClass studentEntityClass = studentRepository.findStudentById(studentId).orElseThrow(() -> new RuntimeException("Student with given Id not Found!"));
        List<SubjectEntityClass> subjects = subjectRepository.findAllById(subjectIds);
        studentEntityClass.getSubjects().addAll(subjects);
        return studentRepository.save(studentEntityClass);
    }

}
