package eci.rappi.rappihackathon.controller;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrdersController {

    public Order convertFindIterable(Document doc) {
        ObjectId _id = doc.getObjectId("_id");
        Double id = doc.getDouble("id");
        Double lat = doc.getDouble("lat");
        Double lng = doc.getDouble("lng");
        String timestamp = doc.getString("timestamp");
        String created_at = doc.getString("created_at");
        String type = doc.getString("type");
        Toolkit toolkit = convertToolkit(((Document) doc.get("toolkit")));

        Order o = new Order(_id, id, lat, lng, timestamp, created_at, type, toolkit);
        return o;
    }

    public Toolkit convertToolkit(Document tool) {
        Double delivery_kit = tool.getDouble("delivery_kit");
        Double kit_size = tool.getDouble("kit_size");
        Double terminal = tool.getDouble("terminal");
        Double know_how = tool.getDouble("know_how");
        Boolean trusted = tool.getBoolean("trusted");
        Double order_level = tool.getDouble("order_level");
        Double storekeeper_level = tool.getDouble("storekeeper_level");
        Double vehicle = tool.getDouble("vehicle");
        Boolean cashless = tool.getBoolean("cashless");
        Boolean exclusive = tool.getBoolean("exclusive");

        Toolkit res = new Toolkit(delivery_kit, kit_size, terminal, know_how, trusted, order_level, storekeeper_level, vehicle, cashless, exclusive);
        return res;
    }

    @RequestMapping(value = "/quantity", method = RequestMethod.GET)
    public Long getAmountOfOrders() {
        System.out.println("Obteniendo todas las ordenes");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoCollection<Document> collection = (MongoCollection<Document>) applicationContext.getBean("mongoCollection");

        return collection.countDocuments();
    }

    @GetMapping("/valores")
    public List<Order> getAllOrders() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoCollection<Document> collection = (MongoCollection<Document>) applicationContext.getBean("mongoCollection");

        List<Order> list = new ArrayList<>();
        for (Document doc:  collection.find()) {
            list.add(convertFindIterable(doc));
        }

        System.out.println(list.size());

        return list;
    }
}
