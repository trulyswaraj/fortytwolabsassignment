package com.fortytwolabs.School_Management_Project.Resources;

import com.fortytwolabs.School_Management_Project.CustomThreadPool;
import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.Service.TeacherService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/teachers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherResource {

    private final TeacherService teacherService;

    public TeacherResource() {
        this.teacherService = new TeacherService(); // plain instantiation
    }

    @GET
    public void getAllTeachers(@Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(() -> {
            try {
                List<TeacherEntityClass> teachers = teacherService.getAllTeachers();
                asyncResponse.resume(Response.ok(teachers).build());
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error While Fetching All Teachers : " + e.getMessage()).build());
            }
        });
    }

    @GET
    @Path("/{id}")
    public void getTeacherById(@PathParam("id") Long id, @Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try {
                    TeacherEntityClass teacher = teacherService.getTeacherById(id);
                    asyncResponse.resume(Response.ok(teacher).build());
                } catch (Exception e) {
                    asyncResponse.resume(Response.status(Response.Status.NOT_FOUND)
                            .entity("Teacher with given id : " + id + " not found.")
                            .build());
                }
            }
        });

    }

    @POST
    public void createTeacher(TeacherEntityClass teacher, @Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try {
                    teacherService.addTeacher(teacher);
                    asyncResponse.resume(Response.status(Response.Status.CREATED)
                            .entity(teacher)
                            .build());
                } catch (Exception e) {
                    asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error Creating Teacher : " + e.getMessage())
                            .build());
                }
            }
        });
    }

    @PUT
    @Path("/{id}")
    public void updateTeacher(@PathParam("id") Long id, TeacherEntityClass teacherDetails, @Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try {
                    TeacherEntityClass existing = teacherService.getTeacherById(id);
                    if (existing == null) {
                        asyncResponse.resume(Response.status(Response.Status.NOT_FOUND)
                                .entity("Teacher Not Found!")
                                .build());
                        return;
                    }
                    existing.setTeacherName(teacherDetails.getTeacherName());
                    existing.setTeacherEmail(teacherDetails.getTeacherEmail());
                    teacherService.updateTeacher(existing);
                    asyncResponse.resume(Response.ok(existing).build());
                } catch (Exception e) {
                    asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error While Updating Teacher : " + e.getMessage())
                            .build());
                }
            }
        });
    }

    @DELETE
    @Path("/{id}")
    public void deleteTeacher(@PathParam("id") Long id, @Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try {
                    TeacherEntityClass existing = teacherService.getTeacherById(id);
                    if (existing == null) {
                        asyncResponse.resume(Response.status(Response.Status.NOT_FOUND)
                                .entity("Teacher With given id " + id + " not found!")
                                .build());
                        return;
                    }
                    teacherService.deleteTeacher(id);
                    asyncResponse.resume(Response.noContent().build());
                } catch (Exception e) {
                    asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error Deleting Teacher : " + e.getMessage())
                            .build());
                }
            }
        });
    }

    // Assign a subject to a teacher
    @POST
    @Path("/{teacherId}/subjects/{subjectId}")
    public void assignTeacherToSubject(@PathParam("teacherId") Long teacherId,
                                       @PathParam("subjectId") Long subjectId,
                                       @Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try {
                    SubjectEntityClass subject = teacherService.getSubjectById(subjectId); // fetch entity
                    if (subject == null) {
                        asyncResponse.resume(Response.status(Response.Status.NOT_FOUND)
                                .entity("Subject Not Found!")
                                .build());
                        return;
                    }
                    TeacherEntityClass updatedTeacher = teacherService.assignTeacherToSubject(teacherId, subjectId);
                    asyncResponse.resume(Response.ok(updatedTeacher).build());
                } catch (Exception e) {
                    asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error assigning Subject : " + e.getMessage())
                            .build());
                }
            }
        });
    }

    // Assign a student to a teacher
    @POST
    @Path("/{teacherId}/students/{studentId}")
    public void assignTeacherToStudent(@PathParam("teacherId") Long teacherId,
                                       @PathParam("studentId") Long studentId,
                                       @Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try {
                    TeacherEntityClass updatedTeacher = teacherService.assignTeacherToStudent(teacherId, studentId);
                    asyncResponse.resume(Response.ok(updatedTeacher).build());
                } catch (Exception e) {
                    asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error assigning Student : " + e.getMessage())
                            .build());
                }
            }
        });
    }
    // Update teacher's subjects
    @POST
    @Path("/{teacherId}/update-subject/{subjectId}")
    public void updateTeacherSubjects(@PathParam("teacherId") Long teacherId,
                                      @PathParam("subjectId") Long subjectId,
                                      @Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(() -> {
            try {
                TeacherEntityClass updatedTeacher = teacherService.updateTeacherSubjects(teacherId, subjectId);
                asyncResponse.resume(Response.ok(updatedTeacher).build());
            } catch (RuntimeException e) {
                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error Updating the Subjects" + e.getMessage())
                        .build());
            } catch (Exception e){
                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error Updating the Subjects : "+e.getMessage())
                        .build());
            }
        });


    }
}
