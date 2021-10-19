package com.example.demo.Beans;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String author;
    private String type;
    private int release_year;
    private int number_of_pages;
    private String ISBN;
    private int available_stock;

    public Book(){

    }

    public Book(String name, String author, String type, int release_year, int number_of_pages, String ISBN, int available_stock){
        this.name = name;
        this.author = author;
        this.type = type;
        this.release_year = release_year;
        this.number_of_pages = number_of_pages;
        this.ISBN = ISBN;
        this.available_stock = available_stock;
    }

//
//    public Book getBookById(Long id){
//        // makes interogation over database  TODO
//        return new Book();
//    }
//
//    public Book getBookByName(String name){
//        // makes interogation over database  TODO
//        return new Book();
//    }
//
//    public Book getBookByAuthor(String author){
//        // makes interogation over database  TODO
//        return new Book();
//    }
//
//    public Book getBookByReleaseYear(int release_year){
//        // makes interogation over database  TODO
//        return new Book();
//    }

//    public static List<Book> getBooks()
//    {
//        // sql = "Select * from Books;";    TODO
//        //System.out.println("ceva---------------");
//        return List.of(new Book("andrew", "author_name", "gen_liric", 1999, 220, "ISBN-1122-2233", 22));
//    }

//    public void addBook(){
//        // sql = "Insert into Book values($this.name, $this.author.....)";
//        // continue with query TODO
//    }





    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public void setAvailable_stock(int available_stock) {
        this.available_stock = available_stock;
    }
}
