package eci.rappi.rappihackathon;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
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

    public static Statement CreateStatementPostgres() {
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

    public static ResultSet makeQuery(String columns, String table, String condition)  {
        Statement statement = CreateStatementPostgres();
        try {
            return statement != null ? statement.executeQuery("select " + columns + " from " + table + " where " + condition) : null;
        } catch (SQLException e) {
            System.err.println("Existe un error haciendo la consulta "+"select " + columns + " from " + table + " where " + condition);
            e.printStackTrace();
        }
        return null;
    }

}