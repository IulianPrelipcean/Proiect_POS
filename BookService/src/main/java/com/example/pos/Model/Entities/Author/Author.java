package com.example.pos.Model.Entities.Author;

import com.example.pos.Model.Entities.BookAuthor.BookAuthor;
import com.sun.istack.NotNull;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

//    @OneToMany(mappedBy="book_author", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<BookAuthor> bookAuthors = new HashSet<>();



//    @ManyToMany(targetEntity = Author.class)
//    private Set<Book> books = new HashSet<>();

    public Author(){

    }

    public Author(Long id, String first_name, String last_name){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
