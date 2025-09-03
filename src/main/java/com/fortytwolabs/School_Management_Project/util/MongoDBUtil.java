package com.fortytwolabs.School_Management_Project.util;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class MongoDBUtil {

    private static final Datastore datastore;

    static {
        try {
            // MongoDB connection URI
            String uri = "mongodb://W1_87109_Swaraj:manager@localhost:27017/school_management";

            // Create MongoClient using connection string
            ConnectionString connectionString = new ConnectionString(uri);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);

            // Initialize Morphia datastore
            datastore = Morphia.createDatastore(mongoClient, "school_management");

            // Map all entities from your package
            //datastore.getMapper().mapPackage("com.fortytwolabs.School_Management_Project.Entity");

//            datastore.getMapper().map(
//                    com.fortytwolabs.School_Management_Project.Entity.TeacherEntityClass.class,
//                    com.fortytwolabs.School_Management_Project.Entity.SubjectEntityClass.class,
//                    com.fortytwolabs.School_Management_Project.Entity.StudentEntityClass.class
//            );

            // Ensure indexes for annotated fields
            //datastore.ensureIndexes();

            System.out.println("✅ Connected to MongoDB and initialized Morphia Datastore.");
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to initialize MongoDB connection", e);
        }
    }

    public static Datastore getDatastore() {
        return datastore;
    }
}
