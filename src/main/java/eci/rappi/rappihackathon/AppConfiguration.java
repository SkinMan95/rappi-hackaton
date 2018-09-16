package eci.rappi.rappihackathon;

import com.mongodb.Block;
import com.mongodb.client.*;
import eci.rappi.rappihackathon.controller.OrdersController;
import eci.rappi.rappihackathon.model.Order;
import eci.rappi.rappihackathon.model.Toolkit;
import org.bson.Document;
import org.omg.CORBA.Object;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mongodb.DBCursor;

import java.sql.*;
import java.util.*;

import static eci.rappi.rappihackathon.controller.OrdersController.convertOrder;

@Configuration
public class AppConfiguration {
    private Map<String, String> filtersStr = new HashMap<String, String>();
    private Map<String, Long> filtersIn = new HashMap<String, Long>();
    private FindIterable<Document> cursor;

    @Bean
    public MongoDatabase mongoDatabase() {
        MongoClient mongoClient = MongoClients.create("mongodb://hackathonmongo:hackathon2018rappimongodb@mongo-hackathon.eastus2.cloudapp.azure.com:27017/orders?authSource=orders&authMechanism=SCRAM-SHA-1");

        MongoDatabase database = mongoClient.getDatabase("orders");

        MongoCollection<Document> collection = database.getCollection("orders");
        //filtersStr.put("timestamp","2018-09-05 10:00:00");
        //filtersIn.put("toolkit.vehicle", (long) 0.0);
        //filtersStr.put("type","restaurant");
        //filtersIn.put("id", (long) 8570766.0);
//        List<Order> filteredOrders = filterBy(collection);
        return database;
    }

    public Document setFilters() {
        Document doc = new Document();
        for (Map.Entry<String, String> entry : filtersStr.entrySet()) {
            doc.append(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Long> entry : filtersIn.entrySet()) {
            doc.append(entry.getKey(), entry.getValue());
        }
        return doc;
    }

    public List<Order> filterBy(MongoCollection<Document> collection) {
        List<Order> orders = new ArrayList<>();
        cursor = collection.find(setFilters());
        for (Document document : cursor) {
            orders.add(OrdersController.convertOrder(document));
        }
        return orders;
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

    public static ResultSet makeQuery(String columns, String table, String condition) {
        Statement statement = CreateStatementPostgres();
        try {
            return statement != null ? statement.executeQuery("select " + columns + " from " + table + " where " + condition) : null;
        } catch (SQLException e) {
            System.err.println("Existe un error haciendo la consulta " + "select " + columns + " from " + table + " where " + condition);
            e.printStackTrace();
        }
        return null;
    }

}