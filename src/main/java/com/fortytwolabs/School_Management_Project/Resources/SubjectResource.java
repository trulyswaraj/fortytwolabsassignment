package com.fortytwolabs.School_Management_Project.Resources;

import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Service.SubjectService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

@Path("/api/subjects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubjectResource {

    private SubjectService subjectService;

    public SubjectResource() {
        this.subjectService = new SubjectService(); // manual instantiation
    }

    // Create a new subject
    @POST
    public Response createSubject(SubjectEntityClass subjectEntityClass) {
        SubjectEntityClass savedSubject = subjectService.save(subjectEntityClass);
        return Response.status(Response.Status.CREATED)
                .entity(Collections.singletonList(savedSubject))
                .build();
    }

    // Get all subjects
    @GET
    public List<SubjectEntityClass> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    // Get subject by ID
    @GET
    @Path("/{id}")
    public Response getSubjectById(@PathParam("id") Long id) {
        SubjectEntityClass subject = subjectService.getSubjectById(id);
        if (subject != null) {
            return Response.ok(subject).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Subject with ID " + id + " not found")
                    .build();
        }
    }
}
