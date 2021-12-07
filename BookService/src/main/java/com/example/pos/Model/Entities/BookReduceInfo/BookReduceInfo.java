package com.example.pos.Model.Entities.BookReduceInfo;

import lombok.Builder;

@Builder
public class BookReduceInfo {

    private String isbn;
    private String title;
    private String genre;

    public BookReduceInfo(){

    }

    public BookReduceInfo(String isbn, String title, String genre){
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
