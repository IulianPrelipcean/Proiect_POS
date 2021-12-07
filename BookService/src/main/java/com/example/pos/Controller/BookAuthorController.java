package com.example.pos.Controller;


import com.example.pos.Model.Entities.Author.Author;
import com.example.pos.Model.Entities.BookAuthor.BookAuthor;
import com.example.pos.Service.BookAuthorService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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


    // get all the authors for a book isbn
    @GetMapping("/books/{isbn}/authors")
    public CollectionModel<EntityModel<Author>> getAuthorsForIsbn(@PathVariable(name="isbn") String isbn){

        List<Author> authorList = bookAuthorService.getAuthorsForIsbn(isbn);
        List<EntityModel<Author>> bookAuthorEntity = authorList.stream()
                .map(author -> EntityModel.of(author,
                        linkTo(methodOn(BookAuthorController.class).getAuthorsForIsbn(isbn)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(bookAuthorEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // add a book and the author
    @PostMapping("/addBookAuthor")
    public ResponseEntity<?> registerNewBookAuthor(@RequestBody BookAuthor bookAuthor){
        BookAuthor bookAuthorSaved = bookAuthorService.addNewBookAuthor(bookAuthor);

        EntityModel<BookAuthor> entityModel = EntityModel.of(bookAuthorSaved,
                linkTo(methodOn(BookAuthorController.class).registerNewBookAuthor(bookAuthorSaved)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}
