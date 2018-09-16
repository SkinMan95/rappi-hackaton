package eci.rappi.rappihackathon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import eci.rappi.rappihackathon.AppConfiguration;
import eci.rappi.rappihackathon.model.Order;
import eci.rappi.rappihackathon.model.Toolkit;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;


@RestController
@RequestMapping("/order")
public class OrdersController {

    private static MongoCollection<Document> collection;

    public OrdersController() {
        System.out.println("Creando objeto de OrdersController");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoCollection<Document> collection = (MongoCollection<Document>) applicationContext.getBean("mongoCollection");

        OrdersController.collection = collection;
    }

    public Order convertOrder(Document doc) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = doc.toJson();
        Order o = null;
        try {
            o = objectMapper.readValue(json, Order.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return o != null ? o : new Order();
    }

    @RequestMapping(value = "/quantity", method = RequestMethod.GET)
    public Long getAmountOfOrders() {
        System.out.println("Obteniendo todas las ordenes");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoCollection<Document> collection = (MongoCollection<Document>) applicationContext.getBean("mongoCollection");

        return collection.countDocuments();
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        for (Document doc:  collection.find()) {
            list.add(convertOrder(doc));
        }

        System.out.println(list.size());

        return list;
    }

    @GetMapping("timestamp")
    public List<Order> getOrdersContainingTimestamp() {
        List<Order> list = new ArrayList<>();

        for (Document doc: collection.find(and(exists("timestamp", true), where("this.timestamp.length==19")))) {
            list.add(convertOrder(doc));
        }

        return list;
    }
}
