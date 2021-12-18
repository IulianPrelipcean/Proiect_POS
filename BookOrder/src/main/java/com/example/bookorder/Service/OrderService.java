package com.example.bookorder.Service;

//
import com.example.bookorder.Model.Book;
import com.example.bookorder.Model.Order;
import com.example.bookorder.Model.OrderStatus;
import com.example.bookorder.Repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

//    public OrderService(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }


    public List<Order> getOrders() {
        List<Order> orderList = orderRepository.findAll();
        return orderList;
//        Optional<Order> optionalCustomer = Optional.ofNullable(orderRepository.findByIdName("ali"));
//        return optionalCustomer.get();
//        return new Order("first", "last");
    }


    public List<Order> getOrdersByClientId(String clientId) {

        // set the name for the collection and check its existence
        orderRepository.setCollectionName(clientId);
        System.out.println("client: " + orderRepository.getCollectionName());

        if (mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            List<Order> orders = orderRepository.findAll();
            return orders;
        } else {
            throw new IllegalStateException("The client doesn't exists!");
        }
    }


    /* TODO  ------------------ de verificat stocul disponibil pt fiecare carte
    comanda -> preia_id_client -> pentru fiecare isbn de carte verific daca e suficient stoc ->(daca DA) -> creez sau adaug un document in colectie
     */
    public void addOrder(Order order, String clientId, String resultBookQuantity) {

        // set the name for the collection and check its existence
        orderRepository.setCollectionName(clientId);

        if (!mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            mongoTemplate.createCollection(orderRepository.getCollectionName());
        }
        order.setLocalDate(LocalDate.now());
        orderRepository.save(order);
    }


    public JSONObject addBookByClientId(Book book, String clientId, String resultBookQuantity) {
        // transform the string into a json
        JSONObject bookJSON = new JSONObject(resultBookQuantity);

        // take the key(isbn)
        Set<String> keys = bookJSON.keySet();
        String bookIsbn = "";
        for (String key : keys) {
            bookIsbn = key;
        }

        if (bookJSON.getInt(bookIsbn) == -1) {       // the quantity ordered is available in the bookStore
            // set the name for the collection and check its existence
            orderRepository.setCollectionName(clientId);

            if (!mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
                mongoTemplate.createCollection(orderRepository.getCollectionName());
            }

            // take all the orders from database to check if there is one in PENDING
            List<Order> orderList = orderRepository.findAll();
            Order orderPending = null;
            if (!orderList.isEmpty()) {
                for (Order order : orderList) {
                    if (order.getOrderStatus() == OrderStatus.PENDING) {
                        orderPending = order;       // an order which is in PENDING has been found
                    }
                }
                if (orderPending != null) {         // append the new book to the existing order
                    boolean existsIsbn = false;
                    for(Book bookFromPending: orderPending.getBookList()){
                        if(bookFromPending.getIsbn().equals(book.getIsbn())){           // check if it is already a book with the same ISBN
                            bookFromPending.setQuantity(bookFromPending.getQuantity() + book.getQuantity());
                            existsIsbn = true;
                        }
                    }
                    if(existsIsbn) {    // if there is an existing ISBN we just have to update it
                        orderRepository.save(orderPending);
                    }
                    else {
                        orderPending.getBookList().add(book);
                        orderRepository.save(orderPending);
                    }


                }
                else{       // the list is not empty, but there is no PENDING order so we have to create a new order
                    List<Book> bookList = new ArrayList<Book>();
                    bookList.add(book);
                    Order newOrder = new Order();
                    newOrder.setLocalDate(LocalDate.now());
                    newOrder.setBookList(bookList);
                    newOrder.setOrderStatus(OrderStatus.PENDING);
                    orderRepository.save(newOrder);

                    // return the book, even if the book was saved successfully
                    return bookJSON;
                }
            } else {       // the list is empty, so we have to create a new order
                List<Book> bookList = new ArrayList<Book>();
                bookList.add(book);
                Order newOrder = new Order();
                newOrder.setLocalDate(LocalDate.now());
                newOrder.setBookList(bookList);
                newOrder.setOrderStatus(OrderStatus.PENDING);
                orderRepository.save(newOrder);

                // return the book, even if the book was saved successfully
                return bookJSON;
            }
        }
        // return the available stock for the book
        return bookJSON;

    }






    public void changeOrderStatus(String clientId, String orderId, OrderStatus orderStatus){
        orderRepository.setCollectionName(clientId);
        if(mongoTemplate.collectionExists(orderRepository.getCollectionName())){
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if(orderOptional.isPresent())
            {
                Order order = orderOptional.get();
                order.setOrderStatus(orderStatus);
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
