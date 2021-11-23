package com.example.bookorder.Model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    public Order findByFirstName(String firstName);

    public Optional<Order> findById(String Id);

    public List<Order> findByLastName(String lastName);

}
