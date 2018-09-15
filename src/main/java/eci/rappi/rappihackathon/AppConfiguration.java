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

    private static Statement conection;

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

    public static void CreateConection() {
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
            conection = con.createStatement();
        }catch(Exception e){
            System.out.println("No se conecto! "+e);
        }
    }

    public static ResultSet makeQuery(String columns, String table, String condition) throws SQLException {
        CreateConection();
        ResultSet res = conection.executeQuery("select "+columns+" from "+table+" where "+condition);
        return res;
    }

    public static ResultSet makeQuery(String columns, String table) throws SQLException {
        CreateConection();
        ResultSet res = conection.executeQuery("select "+columns+" from "+table);
        return res;
    }

    public static void update(String table, String set,String where) throws SQLException {
        CreateConection();
        conection.executeQuery("Update "+table+" set "+set+" where "+where);
    }

}