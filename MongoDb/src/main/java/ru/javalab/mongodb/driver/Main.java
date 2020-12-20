package ru.javalab.mongodb.driver;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

public class Main {
    public static void main(String[] args) {
        MongoClient client = MongoClients.create();
        MongoDatabase mongoDatabase = client.getDatabase("good");
        MongoCollection<Document> collection = mongoDatabase.getCollection("good");
        collection.find()
                .forEach(x -> System.out.println(x.getString("producer")));

        Document searchQuery = new Document();
        searchQuery
                .append("title", new Document("$exists", true))
                .append("$or", Arrays.asList(
                        new Document("price", new Document("$lt", 3000)),
                        new Document("title", "laptop")));

        FindIterable<Document> documents = collection.find(searchQuery)
                .projection(new Document("title", 1)
                        .append("price", 1)
                        .append("producer", 1)
                        .append("_id", 0));

        FindIterable<Document> documents1 = collection.find(
                and(new Document("title", new Document("$exists", true)),
                        or(new Document("producer", "apple"),
                                lt("price",  3500)))
        ).projection(fields(include("title", "producer", "price"), excludeId()));


        for (Document document : documents) {
            System.out.println(document.toJson());
        }
        for (Document document : documents1) {
            System.out.println(document.toJson());
        }

    }
}