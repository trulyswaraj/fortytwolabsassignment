package com.fortytwolabs.School_Management_Project.Resources;

import com.fortytwolabs.School_Management_Project.CustomThreadPool;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Service.SubjectService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/subjects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubjectResource {

    private SubjectService subjectService;


    public SubjectResource() {
        this.subjectService = new SubjectService(); // manual instantiation
    }

    // Create a new subject
    @POST
    public void createSubject(SubjectEntityClass subjectEntityClass, @Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try {
                    SubjectEntityClass savedSubject = subjectService.save(subjectEntityClass);
                    asyncResponse.resume(Response.status(Response.Status.CREATED)
                            .entity(savedSubject)
                            .build());
                } catch (Exception e) {
                    asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error While Creating Subject : " + e.getMessage())
                            .build());
                }
            }
        });
    }

    // Get all subjects
    @GET
    public void getAllSubjects(@Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try{
                    List<SubjectEntityClass> subjects = subjectService.getAllUsingCriteria();
                    asyncResponse.resume(Response.ok(subjects).build());
                } catch (Exception e){
                    asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error while Fetching All subjects : " + e.getMessage())
                            .build());
                }
            }
        });
    }

    // Get subject by ID
    @GET
    @Path("/{id}")
    public void getSubjectById(@PathParam("id") Long id, @Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try{
                    SubjectEntityClass subject = subjectService.getByIdUsingCriteria(id);
                    if(subject != null){
                        asyncResponse.resume(Response.ok(subject).build());
                    } else {
                        asyncResponse.resume(Response.status(Response.Status.NOT_FOUND)
                                .entity("Subject with id "+id+" not found!")
                                .build());
                    }
                } catch (Exception e) {
                    asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error Fetching Subjects : "+ e.getMessage()).build());
                }
            }
        });
    }

    @PUT
    @Path("/{id}")
    public void updateSubject(@PathParam("id") Long id, SubjectEntityClass subjectEntityClass, @Suspended AsyncResponse asyncResponse){
        subjectEntityClass.setId(id);
        CustomThreadPool.getInstance().submitTask(() -> {
            try{

                subjectService.updateSubjectUsingCriteria(subjectEntityClass);
                asyncResponse.resume(
                        Response.ok("Subject With Given Id "+ id + " Updated successfully.").build()
                );
            }catch (Exception e){
                asyncResponse.resume(
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("Error Updating Subject : " + e.getMessage())
                                .build()
                );
            }
        });

    }

    @DELETE
    @Path("/{id}")
    public void deleteSubject(@PathParam("id") Long id, @Suspended AsyncResponse asyncResponse){
        CustomThreadPool.getInstance().submitTask(() -> {
            try{
                subjectService.deleteSubject(id);
                asyncResponse.resume(
                        Response.ok("Subject with Id "+id+" deleted Successfully!").build()
                );
            } catch (Exception e){
                asyncResponse.resume(
                        Response.status(Response.Status.NOT_FOUND)
                                .entity("Subject with id " + id + " not found!" + e.getMessage())
                                .build()
                );
            }
        });
    }


}
