package eci.rappi.rappihackathon;

import eci.rappi.rappihackathon.controller.OrdersController;
import eci.rappi.rappihackathon.model.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersControllerTests {

    OrdersController orders = new OrdersController();

//    @Test
//    /**
//     * Prueba de que la respuesta no sea nula
//     */
//    public void testGetAllOrders() {
//        List<Order> orders = this.orders.getAllOrders();
//
//        Assert.assertNotNull(orders);
//    }
//
//    @Test
//    /**
//     * Se asegura de que z4el timestamp este definido en todas las consultas del metodo
//     * Debe ser de la forma "DD-MM-YYYY HH:MM:SS" (19 caracteres)
//     */
//    public void testAllOrdersHaveTimestamp() {
//        List<Order> orders = this.orders.getOrdersContainingTimestamp();
//
//        Assert.assertNotNull(orders);
//
//        for (Order o: orders) {
//            Assert.assertNotNull(o.getTimestamp());
//            Assert.assertFalse(o.getTimestamp().equals(""));
//            Assert.assertTrue(o.getTimestamp().length() == 19);
//        }
//    }

}
