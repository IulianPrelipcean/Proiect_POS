package com.example.bookorder.Service;

//
import com.example.bookorder.Model.Order;
import com.example.bookorder.Model.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;

//    public OrderService(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }


    public List<Order> getOrders() {
        List<Order> order = orderRepository.findAll();
        return order;
//        Optional<Order> optionalCustomer = Optional.ofNullable(orderRepository.findByIdName("ali"));
//        return optionalCustomer.get();
//        return new Order("first", "last");
    }

    public Order getOrderById(String id){
        Optional<Order> orderById = orderRepository.findById(id);
        return orderById.get();
    }

    public void addOrder(Order order){
        order.localDate = LocalDate.now();
        orderRepository.save(order);
    }


    public void deleteOrders(){
        orderRepository.deleteAll();
    }


}
