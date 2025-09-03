package com.fortytwolabs.School_Management_Project.util;

import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import dev.morphia.Datastore;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class CounterUtil {

    public static Long getNextSequence(String counterName, Datastore datastore){
        Document updatedDoc = datastore.getDatabase()
                .getCollection("counters")
                .findOneAndUpdate(
                        eq("_id", counterName),
                        Updates.inc("seq", 1),
                        new FindOneAndUpdateOptions()
                                .upsert(true)
                                .returnDocument(ReturnDocument.AFTER)
                );

        Object seqValue = updatedDoc.get("seq");
        if(seqValue instanceof  Integer){
            return ((Integer) seqValue).longValue();
        } else if (seqValue instanceof Long) {
            return (Long) seqValue;
        } else {
            throw new IllegalStateException("Unexpected Seq Type : "+ seqValue.getClass());
        }
    }

}
