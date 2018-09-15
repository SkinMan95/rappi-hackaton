package eci.rappi.rappihackathon;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RappiHackathonApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RappiHackathonApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        MongoClient mongoClient = MongoClients.create("mongodb://hackathonmongo:hackathon2018rappimongodb@mongo-hackathon.eastus2.cloudapp.azure.com:27017/orders?authSource=orders&authMechanism=SCRAM-SHA-1");

        MongoDatabase database = mongoClient.getDatabase("orders");

        MongoCollection<Document> collection = database.getCollection("orders");

        System.out.println("Prueba: " + collection.countDocuments());
    }

}
