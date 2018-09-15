package eci.rappi.rappihackathon.data;

import eci.rappi.rappihackathon.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<Order, String> {

    // todo por hacer
}
