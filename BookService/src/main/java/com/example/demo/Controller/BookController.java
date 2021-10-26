package com.example.demo.Controller;

import com.example.demo.Model.Author.Author;
import com.example.demo.Model.Book.Book;
import com.example.demo.View.BookService;
import org.hibernate.event.spi.EntityCopyObserver;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path="api/bookcollection")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
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

    @GetMapping("/authors/{id}")
    public EntityModel<Author> getAuthor(@PathVariable(name="id")Long id){
        return EntityModel.of(bookService.getAuthor(id),
                linkTo(methodOn(BookController.class).getAuthor(id)).withSelfRel(),
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


}
