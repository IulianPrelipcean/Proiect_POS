package com.example.bookorder.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Document(collection="client_1")
public class Order {

    @Id
    public String id;
    public LocalDate localDate;
    public List<Book> bookList;
    public OrderStatus orderStatus;

}
