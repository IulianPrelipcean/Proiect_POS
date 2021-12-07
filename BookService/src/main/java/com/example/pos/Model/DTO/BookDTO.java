package com.example.pos.Model.DTO;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

import com.example.pos.Model.Entities.Book.Book;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO{

    @Id
    @NotNull
    private String isbn;

    @NotNull
    @Column(unique=true)
    private String title;

    @NotNull
    private String genre;

    @NotNull
    private String publisher;

    @NotNull
    private Integer release_year;

    @NotNull
    private int number_of_pages;

    @NotNull
    private double price;

    @NotNull
    private int available_stock;

}
