package com.example.bookorder.Service;


import com.example.bookorder.Model.Customer;
import com.example.bookorder.Model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private  CustomerRepository customerRepository;

//    public OrderService(CustomerRepository customerRepository){
//        this.customerRepository = customerRepository;
//    }

    public Customer getCustomer(){
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByIdName("andre"));
        return optionalCustomer.get();
    }




}
