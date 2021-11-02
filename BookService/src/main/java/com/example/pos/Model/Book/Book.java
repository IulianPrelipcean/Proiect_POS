package com.example.pos.Model.Book;

import com.example.pos.Model.Author.Author;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="book")
public class Book {
    @Id
    @NotNull
    private String isbn;

    @NotNull
    @Column(unique=true)
    private String title;

    @NotNull
    private String type;

    @NotNull
    private String publisher;

    @NotNull
    private int release_year;

    @NotNull
    private int number_of_pages;

    @NotNull
    private double price;

    @NotNull
    private int available_stock;

//    @ManyToMany
//    @JoinTable(name="book_author",
//                joinColumns = {@JoinColumn(name="book_isbn")},
//                inverseJoinColumns = {@JoinColumn(name="author_id")})
//    private Set<Author> authors = new HashSet<>();




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

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
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
