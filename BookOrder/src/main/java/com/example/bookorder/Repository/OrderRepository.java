package com.example.bookorder.Repository;

import com.example.bookorder.Model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>, CustomClient {

    public Optional<Order> findById(String Id);

}
