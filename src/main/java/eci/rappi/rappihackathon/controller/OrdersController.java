package eci.rappi.rappihackathon.controller;

import eci.rappi.rappihackathon.data.OrdersRepository;
import eci.rappi.rappihackathon.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrdersController {

//    @Autowired
//    OrdersRepository repository;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        System.out.println("Obteniendo todas las ordenes");
        return null;
    }
}
