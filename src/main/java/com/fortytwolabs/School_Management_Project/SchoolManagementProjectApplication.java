package com.fortytwolabs.School_Management_Project;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class SchoolManagementProjectApplication extends ResourceConfig {
    public SchoolManagementProjectApplication(){
        packages("com.fortytwolabs.School_Management_Project.Resources");
    }

}
