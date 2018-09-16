package co.edu.escuelaing.is.laboratorio.cambioplan.logic;

import java.lang.reflect.Type;
import java.sql.*;

import com.google.gson.*;
import com.mongodb.DB;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;


public class Utiles {

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
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection(url,user,pass);
            return connection.createStatement();
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

    public static List<StoreKeeper> getStoresKeepers(){
        Statement statement = CreateStatementPostgres();
        List<StoreKeeper> storeKeepers= new ArrayList<>();
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        try {
            ResultSet resultSet = statement.executeQuery("select row_to_json(x) as json from (select * from storekeepers) as x limit 1000");
            while (resultSet.next()){
                storeKeepers.add(gson.fromJson(resultSet.getString("json"),StoreKeeper.class));
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return storeKeepers;

    }

    public static List<Order> getOrders(){
        MongoClient mongoClient = MongoClients.create("mongodb://hackathonmongo:hackathon2018rappimongodb@mongo-hackathon.eastus2.cloudapp.azure.com:27017/orders?authSource=orders&authMechanism=SCRAM-SHA-1");
        MongoDatabase database = mongoClient.getDatabase("orders");
        MongoCollection<Document> collection = database.getCollection("orders");
        Gson gson = new Gson();
        ArrayList<Order> orders= new ArrayList<>();
        for(Document doc : collection.find()){
            Order order = gson.fromJson(doc.toJson(),Order.class);
            orders.add(order);
        }
        return orders;
    }

}
class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        String date = element.getAsString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            return (Date) format.parse(date);
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
            return null;
        }
    }
}