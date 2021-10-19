package com.example.demo.Beans;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="book")
public class Book {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ISBN;
    private String title;
    private String type;
    private int release_year;
    private int number_of_pages;
    private double price;
    private int available_stock;

    public Book(){

    }

    public Book(String ISBN, String title, String type, int release_year, int number_of_pages, double price, int available_stock){
        this.ISBN = ISBN;
        this.title = title;
        this.type = type;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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
