package eci.rappi.rappihackathon.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eci.rappi.rappihackathon.AppConfiguration;
import eci.rappi.rappihackathon.model.Order;
import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;


@RestController
@RequestMapping("/order")
public class OrdersController {
    private static MongoCollection<Document> collection;
    private static MongoDatabase database;

    private static ObjectMapper jsonObjectMapper;

    public OrdersController() {
        System.out.println("Creando objeto de OrdersController");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoDatabase database = (MongoDatabase) applicationContext.getBean("mongoDatabase");
        MongoCollection<Document> collection = database.getCollection("orders");

        OrdersController.collection = collection;
        OrdersController.database = database;

        // ------

        jsonObjectMapper = new ObjectMapper();
    }

    public static Order convertOrder(Document doc) {
        String json = doc.toJson();
        Order o = null;
        try {
            o = jsonObjectMapper.readValue(json, Order.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return o != null ? o : new Order();
    }

    @RequestMapping(value = "/quantity", method = RequestMethod.GET)
    public Long getAmountOfOrders() {
        System.out.println("Obteniendo todas las ordenes");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoCollection<Document> collection = (MongoCollection<Document>) applicationContext.getBean("mongoDatabase");

        return collection.countDocuments();
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            list.add(convertOrder(doc));
        }

        System.out.println(list.size());

        return list;
    }

    @GetMapping("timestamp")
    public List<Order> getOrdersContainingTimestamp() {
        List<Order> list = new ArrayList<>();

        for (Document doc : collection.find(and(exists("timestamp", true), where("this.timestamp.length==19")))) {
            list.add(convertOrder(doc));
        }

        return list;
    }

    @GetMapping("/distinct")
    public List<String> getDistinctField(@RequestParam(value = "field", required = true, defaultValue = "type") String field) throws IOException {
        Document d = database.runCommand(new Document("distinct", "orders").append("key", field));
//        System.out.println(d.toJson());

        return (ArrayList<String>) d.get("values");
    }
}
