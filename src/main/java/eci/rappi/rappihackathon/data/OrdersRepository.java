package eci.rappi.rappihackathon.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<Object, String> {

    // poner contrato aqui
}
