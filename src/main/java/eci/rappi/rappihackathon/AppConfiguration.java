package eci.rappi.rappihackathon;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.Properties;

@Configuration
public class AppConfiguration {

    public static void makeConnection() {
        MongoClient mongoClient = MongoClients.create("mongodb://mongo-hackathon.eastus2.cloudapp.azure.com:27017");

        MongoDatabase database = mongoClient.getDatabase("orders");

        MongoCollection<Document> collection = database.getCollection("orders");

        System.out.println("Prueba: " + collection.countDocuments());
    }

    public static Statement CreateConectionPostgres() {
        try {
            String dataBase = "storekeepersdb";
            String user = "hackathonpostgres";
            String pass = "hackathon2018rappipsql";
            String host = "postgres-hackathon.eastus2.cloudapp.azure.com";
            int port = 5432;
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dataBase;
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", pass);
            Connection con = DriverManager.getConnection(url, props);
            return con.createStatement();
        } catch (Exception e) {
            System.err.println("No se conecto! " + e);
        }
        return null;
    }

    public static ResultSet makeQuery(String columns, String table, String condition) throws SQLException {
        Statement con = CreateConectionPostgres();
        return con != null ? con.executeQuery("select " + columns + " from " + table + " where " + condition) : null;
    }

    public static ResultSet makeQuery(String columns, String table) throws SQLException {
        Statement con = CreateConectionPostgres();
        return con != null ? con.executeQuery("select " + columns + " from " + table) : null;
    }

    public static void update(String table, String set, String where) throws SQLException {
        Statement con = CreateConectionPostgres();
        if (con != null) {
            con.executeQuery("Update " + table + " set " + set + " where " + where);
        }

    }

}