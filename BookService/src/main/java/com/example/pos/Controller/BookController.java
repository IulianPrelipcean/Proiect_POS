package com.example.pos.Controller;

import com.example.pos.Model.Book.Book;
import com.example.pos.Model.BookAuthor.BookAuthor;
import com.example.pos.Model.BookReduceInfo.BookReduceInfo;
import com.example.pos.Service.AuthorService;
import com.example.pos.Service.BookAuthorService;
import com.example.pos.Service.BookService;
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
    private final AuthorService authorService;
    private final BookAuthorService bookAuthorService;

    public BookController(BookService bookService, AuthorService authorService, BookAuthorService bookAuthorService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookAuthorService = bookAuthorService;
    }


    @GetMapping("/booksAll")
    public CollectionModel<Book> getBooks(){
        return CollectionModel.of(bookService.getBooks(),
                linkTo(methodOn(BookController.class).getBooks()).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }

    @RequestMapping(value="/books/{isbn}", method=RequestMethod.GET)
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


    // de mutat in BookAuthorController !!!
    @PostMapping("/addBookAuthor")
    public ResponseEntity<?> registerNewBookAuthor(@RequestBody BookAuthor bookAuthor){
        BookAuthor bookAuthorSaved = bookAuthorService.addNewBookAuthor(bookAuthor);

        EntityModel<BookAuthor> entityModel = EntityModel.of(bookAuthorSaved,
                linkTo(methodOn(BookController.class).registerNewBookAuthor(bookAuthorSaved)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


//    @GetMapping("/books")
    @RequestMapping(value="/books", params={"page", "items_per_page"}, method=RequestMethod.GET)
    public CollectionModel<Book> getBooksPerPage(@RequestParam(name="page") int page,
                                @RequestParam(name="items_per_page") int items_per_page){

        List<Book> bookList = bookService.getBooksPerPage(page, items_per_page);

        System.out.println("nu a ergs");

        return CollectionModel.of(bookList,
                linkTo(methodOn(BookController.class).getBooksPerPage(page, items_per_page)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }

    //@GetMapping("/books")
    @RequestMapping(value="/books", params="page", method=RequestMethod.GET)
    public CollectionModel<Book> getBooksPerPageWithItemsByDefault(@RequestParam(name="page") int page){

        int default_items_per_page = 2;
        List<Book> bookList = bookService.getBooksPerPage(page, default_items_per_page);

        return CollectionModel.of(bookList,
                linkTo(methodOn(BookController.class).getBooksPerPageWithItemsByDefault(page)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    @RequestMapping(value="/books", params="genre", method=RequestMethod.GET)
    public CollectionModel<Book> getBooksByGenre(@RequestParam(name="genre") String genre){
        return CollectionModel.of(bookService.getBooksByGenre(genre),
                linkTo(methodOn(BookController.class).getBooksByGenre(genre)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    @RequestMapping(value="/books", params="year", method=RequestMethod.GET)
    public CollectionModel<Book> getBooksByYear(@RequestParam(name="year") Integer year){
        return CollectionModel.of(bookService.getBooksByYear(year),
                linkTo(methodOn(BookController.class).getBooksByYear(year)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    @RequestMapping(value="/books", params={"genre", "year"}, method=RequestMethod.GET)
    public CollectionModel<Book> getBooksByGenreAndYear(@RequestParam(name="year") Integer year,
                                            @RequestParam(name="genre")String genre){
        return CollectionModel.of(bookService.getBooksByGenreAndYear(genre, year),
                linkTo(methodOn(BookController.class).getBooksByGenreAndYear(year, genre)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    @RequestMapping(value="/books/{isbn}", params="verbose", method=RequestMethod.GET)
    public EntityModel<BookReduceInfo> getBooksByIsbnVerboseFalse(@PathVariable(name="isbn")String isbn,
                                                                  @RequestParam(name="verbose") String verbose)
    {
        return EntityModel.of(bookService.getBooksByIsbnVerboseFalse(isbn),
                linkTo(methodOn(BookController.class).getBooksByIsbn(isbn)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }



}
