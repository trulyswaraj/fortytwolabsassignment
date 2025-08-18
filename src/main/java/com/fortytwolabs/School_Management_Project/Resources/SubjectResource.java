package com.fortytwolabs.School_Management_Project.Resources;

import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Service.SubjectService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Path("/api/subjects")
@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class SubjectResource {
    private final SubjectService subjectService;

    public SubjectResource(SubjectService subjectService){
        this.subjectService=subjectService;
    }

    @POST
    public List<SubjectEntityClass> createSubject(SubjectEntityClass subjectEntityClass){
        return Collections.singletonList(subjectService.saveSubject(subjectEntityClass));
    }

    @GET
    public List<SubjectEntityClass> getAllSubjects(){
        return subjectService.getAllSubjects();
    }

    @GET
    @Path("/{id}")
    public ResponseEntity<SubjectEntityClass> getSubjectById(@PathParam("id") Long id){
        Optional<SubjectEntityClass> subject = subjectService.getSubjectById(id);

        if(subject.isPresent()){
            return ResponseEntity.ok(subject.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
