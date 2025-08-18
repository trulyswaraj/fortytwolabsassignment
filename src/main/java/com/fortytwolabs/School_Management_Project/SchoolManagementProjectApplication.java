package com.fortytwolabs.School_Management_Project;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SchoolManagementProjectApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
        SpringApplication.run(SchoolManagementProjectApplication.class, args);
	}

    @Bean
    public ResourceConfig jerseyConfig(){
        return new ResourceConfig().packages("com.fortytwolabs.School_Management_Project.Resources");
    }
}
