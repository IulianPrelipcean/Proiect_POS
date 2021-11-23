package com.example.bookorder.Model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="client_1")
public class Order {

    @Id
    public String id;
    public String firstName;
    public String lastName;

    public Order(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
