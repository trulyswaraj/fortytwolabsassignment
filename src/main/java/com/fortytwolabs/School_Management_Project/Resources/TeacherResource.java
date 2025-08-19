package com.fortytwolabs.School_Management_Project.Resources;

import com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass;
import com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass;
import com.fortytwolabs.School_Management_Project.Service.TeacherService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;


@Path("/api/teachers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherResource {

    private final TeacherService teacherService;

    public TeacherResource() {
        this.teacherService = new TeacherService(); // plain instantiation
    }

    @GET
    public Response getAllTeachers() {
        List<TeacherEntityClass> teachers = teacherService.getAllTeachers();
        return Response.ok(teachers).build();
    }

    @GET
    @Path("/{id}")
    public Response getTeacherById(@PathParam("id") Long id) {
        TeacherEntityClass teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            return Response.ok(teacher).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Teacher not found with id: " + id)
                    .build();
        }
    }

    @POST
    public Response createTeacher(TeacherEntityClass teacher) {
        teacherService.addTeacher(teacher);
        return Response.status(Response.Status.CREATED).entity(teacher).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTeacher(@PathParam("id") Long id, TeacherEntityClass teacherDetails) {
        TeacherEntityClass existing = teacherService.getTeacherById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Teacher not found")
                    .build();
        }
        existing.setTeacherName(teacherDetails.getTeacherName());
        existing.setTeacherEmail(teacherDetails.getTeacherEmail());
        teacherService.updateTeacher(existing);
        return Response.ok(existing).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTeacher(@PathParam("id") Long id) {
        TeacherEntityClass existing = teacherService.getTeacherById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Teacher not found")
                    .build();
        }
        teacherService.deleteTeacher(id);
        return Response.noContent().build();
    }

    // Assign a subject to a teacher
    @POST
    @Path("/{teacherId}/subjects/{subjectId}")
    public Response assignSubjectToTeacher(@PathParam("teacherId") Long teacherId,
                                           @PathParam("subjectId") Long subjectId) {
        SubjectEntityClass subject = teacherService.getSubjectById(subjectId); // fetch entity
        if (subject == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Subject not found")
                    .build();
        }
        TeacherEntityClass updatedTeacher = teacherService.assignTeacherToSubject(teacherId, subject);
        return Response.ok(updatedTeacher).build();
    }

    // Assign a student to a teacher
    @POST
    @Path("/{teacherId}/students/{studentId}")
    public Response assignStudentToTeacher(@PathParam("teacherId") Long teacherId,
                                           @PathParam("studentId") Long studentId) {
        StudentEntityClass student = teacherService.getStudentById(studentId); // fetch entity
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Student not found")
                    .build();
        }
        TeacherEntityClass updatedTeacher = teacherService.assignTeacherToStudent(teacherId, student);
        return Response.ok(updatedTeacher).build();
    }

    // Update teacher's subjects
    @POST
    @Path("/{teacherId}/update-subject/{subjectId}")
    public Response updateTeacherSubjects(@PathParam("teacherId") Long teacherId,
                                          @PathParam("subjectId") Long subjectId) {
        SubjectEntityClass subject = teacherService.getSubjectById(subjectId); // fetch entity
        if (subject == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Subject not found")
                    .build();
        }
        TeacherEntityClass updatedTeacher = teacherService.updateTeacherSubjects(teacherId, subject);
        return Response.ok(updatedTeacher).build();
    }
}
