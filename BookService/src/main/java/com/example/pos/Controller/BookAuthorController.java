package com.example.pos.Controller;


import com.example.pos.Model.Author.Author;
import com.example.pos.Model.BookAuthor.BookAuthor;
import com.example.pos.Service.AuthorService;
import com.example.pos.Service.BookAuthorService;
import com.example.pos.Service.BookService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path="api/bookcollection")
public class BookAuthorController {

//    private final BookService bookService;
//    private final AuthorService authorService;
    private final BookAuthorService bookAuthorService;

//    public BookAuthorController(BookService bookService, AuthorService authorService, BookAuthorService bookAuthorService){
//        this.bookService = bookService;
//        this.authorService = authorService;
//        this.bookAuthorService = bookAuthorService;
//    }

    public BookAuthorController(BookAuthorService bookAuthorService){
        this.bookAuthorService = bookAuthorService;
    }


    //public CollectionModel<Author> getAuthorsForIsbn(@PathVariable(name="isbn") String isbn){

    @GetMapping("/books/{isbn}/authors")
    public CollectionModel<Author> getAuthorsForIsbn(@PathVariable(name="isbn") String isbn){
//    public void getAuthorsForIsbn(@PathVariable(name="isbn") String isbn){

        List<Author> authorList = bookAuthorService.getAuthorsForIsbn(isbn);
        for(Author author: authorList){
            System.out.println("in controller: ---- " + author.getLastName());
        }


        return CollectionModel.of(bookAuthorService.getAuthorsForIsbn(isbn),
                linkTo(methodOn(BookAuthorController.class).getAuthorsForIsbn(isbn)).withSelfRel());
                //linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }



}
