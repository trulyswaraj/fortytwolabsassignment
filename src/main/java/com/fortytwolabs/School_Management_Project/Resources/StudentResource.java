package com.fortytwolabs.School_Management_Project.Resources;

import com.fortytwolabs.School_Management_Project.CustomThreadPool;
import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Service.StudentService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService studentService = new StudentService();

      // Get All Students
    @GET
    public Response getAllStudents(@QueryParam("page") @DefaultValue("1") int page,
                                                   @QueryParam("size") @DefaultValue("10") int size) {

        List<StudentEntityClass> students = studentService.getAllStudents(page, size);
        long totalItems = studentService.getTotalStudents();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        Map<String, Object> response = new HashMap<>();
        response.put("Data", students);
        response.put("Total Items", totalItems);
        response.put("Page Number",page);
        response.put("Page Size", size);
        response.put("TotalPages", totalPages);
        return Response.ok(response).build();
    }

    // Get Student By Id
    @GET
    @Path("/{id}")
   public void getStudentById(@PathParam("id") Long id, @Suspended final AsyncResponse asyncResponse){
        System.out.println("[Server Thread] - "  + Thread.currentThread().getName());
        asyncResponse.setTimeout(90, TimeUnit.SECONDS);

        CustomThreadPool.getInstance().submitTask(()->{
            try{
                StudentEntityClass studentEntityClass = studentService.getStudentById(id);
                asyncResponse.resume(Response.ok(studentEntityClass).build());
            } catch (Exception e) {
                asyncResponse.resume(
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("Error : "+e.getMessage())
                                .build());
            }
        });
    }

    // Create A Student
    @POST
    public void createStudent(StudentEntityClass studentEntityClass, @Suspended final AsyncResponse asyncResponse) {
            System.out.println("[Server Thread] - "+Thread.currentThread().getName());
            asyncResponse.setTimeout(90, TimeUnit.SECONDS);
            CustomThreadPool.getInstance().submitTask(new Runnable() {
                @Override
                public void run() {
                    try{
                        StudentEntityClass createdStudent = studentService.addStudent(studentEntityClass);
                        asyncResponse.resume(
                                Response.status(Response.Status.CREATED)
                                        .entity(createdStudent)
                                        .build());
                    } catch (Exception e) {
                        asyncResponse.resume(
                                Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .entity("Error : " + e.getMessage())
                                        .build()
                        );
                    }
                }
            });
    }

    // Update A Student
    @PUT
    @Path("/{id}")
    public void updateStudent(@PathParam("id") Long id, StudentEntityClass studentEntityClass, @Suspended AsyncResponse asyncResponse) {

        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try{

                    StudentEntityClass updatedStudent = studentService.updateStudent(id,studentEntityClass);
                    if(updatedStudent != null){
                        asyncResponse.resume(Response.ok(updatedStudent).build());
                    } else {
                        asyncResponse.resume(Response.status(Response.Status.NOT_FOUND)
                                .entity("Student with Id " + id+ " Not found!")
                                .build());
                    }
                } catch (Exception e){
                    asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error Updating Student: "+ e.getMessage())
                            .build());
                }
            }
        });
    }

    // Delete A Student
    @DELETE
    @Path("/{id}")
    public void deleteStudentById(@PathParam("id") Long id, @Suspended AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                try {
                    studentService.deleteStudent(id);
                    asyncResponse.resume(Response.ok("Student With id " + id + " deleted successfully!").build());
                } catch (Exception e) {
                    asyncResponse.resume(Response.status(Response.Status.NOT_FOUND)
                            .entity("Student with Id " + id + " not Found!" + e.getMessage())
                            .build());
                }
            }
        });
    }

    @POST
    @Path("/{studentId}/teachers/{teacherId}")
    public void assignTeacherToStudent(@PathParam("studentId") Long studentId,
                                       @PathParam("teacherId") Long teacherId,
                                       @Suspended final AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(() -> {
            try {
                StudentEntityClass updatedStudent = studentService.assignTeacherToStudent(studentId, teacherId);

                if (updatedStudent != null) {
                    asyncResponse.resume(Response.ok(updatedStudent).build());
                } else {
                    asyncResponse.resume(Response.status(Response.Status.NOT_FOUND)
                            .entity("Student or Teacher not found!").build());
                }
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error assigning teacher: " + e.getMessage()).build());
            }
        });
    }

    @POST
    @Path("/{studentId}/subjects/{subjectId}")
    public void assignSubjectToStudent(@PathParam("studentId") Long studentId,
                                       @PathParam("subjectId") Long subjectId,
                                       @Suspended final AsyncResponse asyncResponse) {
        CustomThreadPool.getInstance().submitTask(() -> {
            try {
                StudentEntityClass updatedStudent = studentService.assignSubjectToStudent(studentId, subjectId);
                if (updatedStudent != null) {
                    asyncResponse.resume(Response.ok(updatedStudent).build());
                } else {
                    asyncResponse.resume(Response.status(Response.Status.NOT_FOUND)
                            .entity("Student or Subject not found!").build());
                }
            } catch (Exception e) {
                asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error assigning subject: " + e.getMessage()).build());
            }
        });
    }


}
