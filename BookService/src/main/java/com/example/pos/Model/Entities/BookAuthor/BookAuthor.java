package com.example.pos.Model.Entities.BookAuthor;

import com.example.pos.Model.Entities.Author.Author;
import com.example.pos.Model.Entities.Book.Book;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="book_author")
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int index_author;


    @ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name="isbn", nullable=false)
    @JoinColumn(referencedColumnName = "isbn")
    private Book book;

    @ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name="id_author", nullable=false)
    @JoinColumn(referencedColumnName = "id")
    private Author author;


    public BookAuthor(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIndex_author() {
        return index_author;
    }

    public void setIndex_author(int index_author) {
        this.index_author = index_author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
