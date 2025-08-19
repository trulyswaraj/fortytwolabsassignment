package com.fortytwolabs.School_Management_Project.Resources;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Service.StudentService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService studentService = new StudentService();

    // Get All Students
    @GET
    public List<StudentEntityClass> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get Student By Id
    @GET
    @Path("/{id}")
    public Response getStudentById(@PathParam("id") Long id) {
        StudentEntityClass student = studentService.getStudentById(id);
        if (student != null) {
            return Response.ok(student).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Student not found with id: " + id)
                    .build();
        }
    }

    // Create A Student
    @POST
    public Response createStudent(StudentEntityClass studentEntityClass) {
        try {
            studentService.addStudent(studentEntityClass);
            return Response.status(Response.Status.CREATED).entity(studentEntityClass).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating student")
                    .build();
        }
    }

    // Update A Student
    @PUT
    @Path("/{id}")
    public Response updateStudent(@PathParam("id") Long id, StudentEntityClass studentEntityClass) {
        StudentEntityClass existing = studentService.getStudentById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
        existing.setName(studentEntityClass.getName());
        existing.setEmail(studentEntityClass.getEmail());

        studentService.updateStudent(existing);
        return Response.ok(existing).build();
    }

    // Delete A Student
    @DELETE
    @Path("/{id}")
    public Response deleteStudentById(@PathParam("id") Long id) {
        StudentEntityClass student = studentService.getStudentById(id);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
        studentService.deleteStudent(id);
        return Response.noContent().build();
    }
}
