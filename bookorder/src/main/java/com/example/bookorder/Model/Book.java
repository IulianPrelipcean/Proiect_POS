package com.example.bookorder.Model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Book {
    String isbn;
    String title;
    Double price;
    Integer quantity;
}
