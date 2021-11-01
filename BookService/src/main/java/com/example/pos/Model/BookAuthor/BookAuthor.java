package com.example.pos.Model.BookAuthor;

import javax.persistence.*;

@Entity
@Table(name="book_author")
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String isbn;
    private int id_author;
    private int indexAuthor;

    public BookAuthor(){

    }
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getId_author() {
        return id_author;
    }

    public void setId_author(int id_author) {
        this.id_author = id_author;
    }

    public int getIndex() {
        return indexAuthor;
    }

    public void setIndex(int indexAuthor) {
        this.indexAuthor = indexAuthor;
    }
}
