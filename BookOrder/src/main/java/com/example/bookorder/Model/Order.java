package com.example.bookorder.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Document(collection="#{@orderRepository.getCollectionName()}")
public class Order {

    @Id
    private String id;
    private LocalDate localDate;
    private List<Book> bookList;
    private OrderStatus orderStatus;

    public Order(){
    }

    public Order(LocalDate localDate, List<Book> bookList, OrderStatus orderStatus){
        this.localDate = localDate;
        this.bookList = bookList;
        this.orderStatus = orderStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
