package com.example.pos.Controller;

import com.example.pos.Model.Author.Author;
import com.example.pos.Model.Book.Book;
import com.example.pos.Model.BookAuthor.BookAuthor;
import com.example.pos.Service.AuthorService;
import com.example.pos.Service.BookAuthorService;
import com.example.pos.Service.BookService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path="api/bookcollection")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookAuthorService bookAuthorService;

    public BookController(BookService bookService, AuthorService authorService, BookAuthorService bookAuthorService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookAuthorService = bookAuthorService;
    }

//    @GetMapping("/books")
//    public ResponseEntity<List<Book>> getBooks(){
//        return ResponseEntity.ok(bookService.getBooks());
//    }
//
//    @GetMapping("/books/{isbn}")
//    public ResponseEntity<Book> getBooksByIsbn(@PathVariable(name="isbn")String isbn)
//    {
//        return ResponseEntity.ok(bookService.getBooksByIsbn(isbn));
//    }
//
//    @GetMapping("/authors/{ID}")
//    public ResponseEntity<Author> getAuthor(@PathVariable(name="ID")Long ID){
//        return ResponseEntity.ok(bookService.getAuthor(ID));
//    }
//
//    @PostMapping("/addBook")
//    public void registerNewBook(@RequestBody Book book){
//        bookService.addNewBook(book);
//    }



    @GetMapping("/books")
    public CollectionModel<Book> getBooks(){
        return CollectionModel.of(bookService.getBooks(),
                linkTo(methodOn(BookController.class).getBooks()).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }

    @GetMapping("/books/{isbn}")
    public EntityModel<Book> getBooksByIsbn(@PathVariable(name="isbn")String isbn)
    {
        return EntityModel.of(bookService.getBooksByIsbn(isbn),
                linkTo(methodOn(BookController.class).getBooksByIsbn(isbn)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }

    @PostMapping("/addBook")
    public ResponseEntity<?> registerNewBook(@RequestBody Book book){
        Book bookSaved = bookService.addNewBook(book);

        EntityModel<Book> entityModel = EntityModel.of(bookSaved,
                linkTo(methodOn(BookController.class).getBooksByIsbn(bookSaved.getIsbn())).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @PostMapping("/addBookAuthor")
    public ResponseEntity<?> registerNewBookAuthor(@RequestBody BookAuthor bookAuthor){
        BookAuthor bookAuthorSaved = bookAuthorService.addNewBookAuthor(bookAuthor);

        EntityModel<BookAuthor> entityModel = EntityModel.of(bookAuthorSaved,
                linkTo(methodOn(BookController.class).registerNewBookAuthor(bookAuthorSaved)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }








}
