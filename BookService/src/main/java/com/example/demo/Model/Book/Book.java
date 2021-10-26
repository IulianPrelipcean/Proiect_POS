package com.example.demo.Model.Book;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="book")
public class Book {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String isbn;
    private String title;
    private String type;
    private String publisher;
    private int release_year;
    private int number_of_pages;
    private double price;
    private int available_stock;

    public Book(){

    }

    public Book(String isbn,
                String title,
                String type,
                String publisher,
                int release_year,
                int number_of_pages,
                double price,
                int available_stock){
        this.isbn = isbn;
        this.title = title;
        this.type = type;
        this.publisher = publisher;
        this.release_year = release_year;
        this.number_of_pages = number_of_pages;
        this.price = price;
        this.available_stock = available_stock;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublisher(){
        return publisher;
    }

    public void setPublisher(String publisher){
        this.publisher = publisher;
    }


    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public int getNumber_of_pages() {
        return number_of_pages;
    }

    public void setNumber_of_pages(int number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAvailable_stock() {
        return available_stock;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return this.price;
    }

    public void setAvailable_stock(int available_stock) {
        this.available_stock = available_stock;
    }
}
