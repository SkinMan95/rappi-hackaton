package eci.rappi.rappihackathon.data;

import eci.rappi.rappihackathon.model.Order;
import org.bson.types.ObjectId;

import java.util.List;

public interface OrdersRepository {

//    @Query("{ 'id' : { $gt: 0 } }")
//    List<Order> showAll();

    Order findBy_id(ObjectId _id);
}
