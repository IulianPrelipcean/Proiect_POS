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
import java.util.stream.Collectors;

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


    //@GetMapping("/booksAll")
    // return all the books
    @RequestMapping("/books")
    public CollectionModel<EntityModel<Book>> getBooks(){

        List<Book> booksList = bookService.getBooks();

        List<EntityModel<Book>> booksEntity = booksList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksByIsbn(book.getIsbn())).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // return the books based on ISBN
    @RequestMapping(value="/books/{isbn}", method=RequestMethod.GET)
    public EntityModel<Book> getBooksByIsbn(@PathVariable(name="isbn")String isbn)
    {
        return EntityModel.of(bookService.getBooksByIsbn(isbn),
                linkTo(methodOn(BookController.class).getBooksByIsbn(isbn)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // add a book in database with all the field completed
    @PostMapping("/addBook")
    public ResponseEntity<?> registerNewBook(@RequestBody Book book){
        Book bookSaved = bookService.addNewBook(book);

        EntityModel<Book> entityModel = EntityModel.of(bookSaved,
                linkTo(methodOn(BookController.class).getBooksByIsbn(bookSaved.getIsbn())).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }





//    @GetMapping("/books")
    //  return the books based on page and items per page (pagination)
    @RequestMapping(value="/books", params={"page", "items_per_page"}, method=RequestMethod.GET)
    public CollectionModel<EntityModel<Book>> getBooksPerPage(@RequestParam(name="page") int page,
                                                @RequestParam(name="items_per_page") int items_per_page){

        List<Book> booksList = bookService.getBooksPerPage(page, items_per_page);

        List<EntityModel<Book>> booksEntity = booksList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksPerPage(page, items_per_page)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }

    //@GetMapping("/books")
    // //  return the books based on page and with a <default> items per page
    @RequestMapping(value="/books", params="page", method=RequestMethod.GET)
    public CollectionModel<EntityModel<Book>> getBooksPerPageWithItemsByDefault(@RequestParam(name="page") int page){

        int default_items_per_page = 2;
        List<Book> booksList = bookService.getBooksPerPage(page, default_items_per_page);

        List<EntityModel<Book>> booksEntity = booksList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksPerPageWithItemsByDefault(page)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // return the books based on genre
    @RequestMapping(value="/books", params="genre", method=RequestMethod.GET)
    public CollectionModel<EntityModel<Book>> getBooksByGenre(@RequestParam(name="genre") String genre){

        List<Book> booksList = bookService.getBooksByGenre(genre);

        List<EntityModel<Book>> booksEntity = booksList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksByGenre(genre)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // return the books based on year
    @RequestMapping(value="/books", params="year", method=RequestMethod.GET)
    public CollectionModel<EntityModel<Book>> getBooksByYear(@RequestParam(name="year") Integer year){
        List<Book> booksList = bookService.getBooksByYear(year);

        List<EntityModel<Book>> booksEntity = booksList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksByYear(year)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // return the books based on genre and year
    @RequestMapping(value="/books", params={"genre", "year"}, method=RequestMethod.GET)
    public CollectionModel<EntityModel<Book>> getBooksByGenreAndYear(@RequestParam(name="year") Integer year,
                                            @RequestParam(name="genre")String genre){

        List<Book> booksList = bookService.getBooksByGenreAndYear(year, genre);

        List<EntityModel<Book>> booksEntity = booksList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksByGenreAndYear(year, genre)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // return a book based on ISBN but with just some of the details(ISBN, title, genre), not all of them
    @RequestMapping(value="/books/{isbn}", params="verbose", method=RequestMethod.GET)
    public EntityModel<BookReduceInfo> getBooksByIsbnVerboseFalse(@PathVariable(name="isbn")String isbn,
                                                                  @RequestParam(name="verbose") String verbose)
    {
        return EntityModel.of(bookService.getBooksByIsbnVerboseFalse(isbn),
                linkTo(methodOn(BookController.class).getBooksByIsbn(isbn)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }



}
