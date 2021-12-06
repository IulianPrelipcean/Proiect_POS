package com.example.bookorder.Service;

//
import com.example.bookorder.Model.Order;
import com.example.bookorder.Model.OrderStatus;
import com.example.bookorder.Repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

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


    public List<Order> getOrdersByClientId(String clientId){

        // set the name for the collection and check its existence
        orderRepository.setCollectionName(clientId);
        System.out.println("client: " + orderRepository.getCollectionName());

        if(mongoTemplate.collectionExists(orderRepository.getCollectionName())){
            List<Order> orders = orderRepository.findAll();
            return orders;
        }
        else{
            throw new IllegalStateException("The client doesn't exists!");
        }




    }


    /*
    comanda -> preia_id_client -> pentru fiecare isbn de carte verific daca e suficient stoc ->(daca DA) -> creez sau adaug un document in colectie
     */
    public void addOrder(Order order, String clientId){

        // set the name for the collection and check its existence
        orderRepository.setCollectionName(clientId);

        if(!mongoTemplate.collectionExists(orderRepository.getCollectionName())){
            mongoTemplate.createCollection(orderRepository.getCollectionName());
        }
        order.setLocalDate(LocalDate.now());
        orderRepository.save(order);
    }


    public void changeOrderStatus(String clientId, String orderId, OrderStatus orderStatus){
        orderRepository.setCollectionName(clientId);
        if(mongoTemplate.collectionExists(orderRepository.getCollectionName())){
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if(orderOptional.isPresent())
            {
                Order order = orderOptional.get();
                order.setOrderStatus(orderStatus);
                System.out.println("orderStatus: " + order.getOrderStatus());
                orderRepository.save(order);
            }
            else{
                throw new IllegalStateException("Comanda nu exista");
            }

        }
        else{
            throw new IllegalStateException("Collection does not exists !");
        }
    }


    public void deleteOrdersByClientId(String clientId){
        orderRepository.setCollectionName(clientId);
        if(!mongoTemplate.collectionExists(orderRepository.getCollectionName())){
            throw new IllegalStateException("Collection does not exists !");
        }
        orderRepository.deleteAll();
    }


    public void deleteOrdersByOrderId(String clientId, String orderId){
        orderRepository.setCollectionName(clientId);
        if(mongoTemplate.collectionExists(orderRepository.getCollectionName())){
           orderRepository.deleteById(orderId);
        }
        else{
            throw new IllegalStateException("Collection does not exists !");
        }
    }


}
