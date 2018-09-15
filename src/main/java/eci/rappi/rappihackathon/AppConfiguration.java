package eci.rappi.rappihackathon;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.sql.*;
import java.util.Properties;

@Configuration
public class AppConfiguration {

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {

        // Set credentials
        MongoCredential credential = MongoCredential.createCredential("hackathonmongo", "orders", "hackathon2018rappimongodb".toCharArray());
        ServerAddress serverAddress = new ServerAddress("mongo-hackathon.eastus2.cloudapp.azure.com", 27017);

        // Mongo Client
        MongoClient mongoClient = new MongoClient(serverAddress, credential, new MongoClientOptions.Builder().build());


        return new SimpleMongoDbFactory(mongoClient, "orders");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        return mongoTemplate;

    }

    public static Statement CreateConectionPostgres() {
        try{
            String dataBase="storekeepersdb";
            String user="hackathonpostgres";
            String pass="hackathon2018rappipsql";
            String host="postgres-hackathon.eastus2.cloudapp.azure.com";
            int port=5432;
            String url = "jdbc:postgresql://"+host+":"+port+"/"+dataBase;
            Properties props = new Properties();
            props.setProperty("user",user);
            props.setProperty("password",pass);
            Connection con = DriverManager.getConnection(url, props);
            return con.createStatement();
        }catch(Exception e){
            System.err.println("No se conecto! "+e);
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

    public static void update(String table, String set,String where) throws SQLException {
        Statement con = CreateConectionPostgres();
        if (con != null) {
            con.executeQuery("Update "+table+" set "+set+" where "+where);
        }
    }

}