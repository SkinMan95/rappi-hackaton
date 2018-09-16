package eci.rappi.rappihackathon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Block;
import com.mongodb.client.*;
import eci.rappi.rappihackathon.controller.OrdersController;
import eci.rappi.rappihackathon.model.Order;
import eci.rappi.rappihackathon.model.StoreKeeper;
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
    private static final double dLat = 0.00085;
    private static final double dLong= 0.000085;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static Map<String, String> filtersStr = new HashMap<>();
    private static Map<String, Long> filtersIn = new HashMap<>();
    private static ObjectMapper jsonObjectMapper;

    @Bean
    public static MongoDatabase mongoDatabase() throws SQLException {
        MongoClient mongoClient = MongoClients.create("mongodb://hackathonmongo:hackathon2018rappimongodb@mongo-hackathon.eastus2.cloudapp.azure.com:27017/orders?authSource=orders&authMechanism=SCRAM-SHA-1");
        MongoDatabase database = mongoClient.getDatabase("orders");
        MongoCollection<Document> collection = database.getCollection("orders");
        AppConfiguration.database = database;
        AppConfiguration.collection = collection;
        List<StoreKeeper> str = getStoreKeepersByOrder("{\n" +
                "  \"_id\" : {\n" +
                "    \"$oid\" : \"5b9c7e50757f72efad81ccb6\"\n" +
                "  },\n" +
                "  \"id\" : 8570766.0,\n" +
                "  \"lat\" : 4.728696,\n" +
                "  \"lng\" : -74.032399,\n" +
                "  \"timestamp\" : \"2018-09-05 10:00:00\",\n" +
                "  \"created_at\" : \"2018-09-05 04:54:44\",\n" +
                "  \"type\" : \"restaurant\",\n" +
                "  \"toolkit\" : {\n" +
                "    \"delivery_kit\" : 0.0,\n" +
                "    \"kit_size\" : 0.0,\n" +
                "    \"terminal\" : 0.0,\n" +
                "    \"know_how\" : 0.0,\n" +
                "    \"trusted\" : false,\n" +
                "    \"order_level\" : 2.0,\n" +
                "    \"storekeeper_level\" : 1.0,\n" +
                "    \"vehicle\" : 0.0,\n" +
                "    \"cashless\" : true,\n" +
                "    \"exclusive\" : false\n" +
                "  }\n" +
                "}");
        return database;
    }

    public static Document setFilters() {
        Document doc = new Document();
        for (Map.Entry<String, String> entry : filtersStr.entrySet()) {
            doc.append(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Long> entry : filtersIn.entrySet()) {
            doc.append(entry.getKey(), entry.getValue());
        }
        return doc;
    }

    public static List<Order> getFilteredOrders(Map<String, String> filterNames) {
        filtersStr.clear();
        filtersIn.clear();

        for (Map.Entry<String, String> entry : filterNames.entrySet()) {
            switch (entry.getKey()) {
                case "timestamp":
                case "created_at":
                case "type":
                    filtersStr.put(entry.getKey(), entry.getValue());
                    break;
                case "toolkit.trusted":
                case "toolkit.cashless":
                case "toolkit.exclusive":
                    filtersIn.put(entry.getKey(), Long.parseLong(entry.getValue()));
                    break;
                default:
                    filtersIn.put(entry.getKey(), Long.parseLong(entry.getValue()));
            }
        }

        return filterBy(AppConfiguration.collection);
    }

    public static List<Order> filterBy(MongoCollection<Document> collection) {
        List<Order> orders = new ArrayList<>();
        FindIterable<Document> cursor = collection.find(setFilters());
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
    public static List<StoreKeeper> getStoreKeepersByOrder(String orderSt) throws SQLException {
        Statement statement = CreateStatementPostgres();
        try{

            List<StoreKeeper> storeKeepers = new ArrayList<>();
            Order o = null;
            jsonObjectMapper = new ObjectMapper();
            o = jsonObjectMapper.readValue(orderSt, Order.class);
            Toolkit tk = o.getToolkit();
            Double oLat = o.getLat();
            Double oLng = o.getLng();
            Double tlat = oLat+dLat;
            Double vlng = oLng+dLong;
            ResultSet rs = statement.executeQuery("select row_to_json(t) from (SELECT * FROM storekeepers " +
                    "WHERE lat <= (" + (tlat) + ") " +
                    "and lat>=(" + (vlng) + ") " +
                    "and lng<=(" + (tlat) + ") " +
                    "and lng>=(" + (vlng) + ")) t");

            //int cont=0;
            while(rs.next()){
                String json = rs.getString(1);
                StoreKeeper sk = jsonObjectMapper.readValue(json, StoreKeeper.class);
                storeKeepers.add(sk);
            }
            //for(StoreKeeper ss : storeKeepers){
              //  if(ss.getToolkit().getTrusted().equals(tk.getTrusted()) && ss.getToolkit().getVehicle().equals(0.0) ){
                //    System.out.println("VEHICLEEEE: "+ tk.getVehicle());
                 //   cont++;
                //}

            //}
            //System.out.println("CONTTTTT: "+cont);
            statement.close();
            return storeKeepers;
        }catch (Exception e){
            e.printStackTrace();
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