package com.example.pos.Controller;

import com.example.pos.Model.DTO.BookDTO;
import com.example.pos.Model.DTO.BookReduceInfoDTO;
import com.example.pos.Model.Entities.Book.Book;
import com.example.pos.Model.Entities.BookAuthor.BookAuthor;
import com.example.pos.Model.Entities.BookReduceInfo.BookReduceInfo;
import com.example.pos.Service.AuthorService;
import com.example.pos.Service.BookAuthorService;
import com.example.pos.Service.BookService;
import org.json.JSONObject;
import org.springframework.boot.json.YamlJsonParser;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "*")
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



    // TODO -- deocamdata e nefolosita(probabil va fi stearsa)
    //return a JSON as a string that contain the Isbn and the quantity of a book
    @GetMapping(value="/bookStock")
    public String getBooksStock(){
        // System.out.println("before ------- ");
        // List<Map<String, Integer>> quantityList = bookService.getBooksQuantity();
        JSONObject jsonObjectBooks  = bookService.getBooksStock();

        String jsonString = jsonObjectBooks.toString();

        //System.out.println("after entity is ------- " + jsonObjectBooks);

        //System.out.println("after string is ------- " + jsonString);


        return jsonString;
    }




    // return all the books
    @GetMapping(value="/books")
    public CollectionModel<EntityModel<BookDTO>> getBooks(){

        List<BookDTO> booksDTOList = bookService.getBooks();

        List<EntityModel<BookDTO>> booksDTOEntity = booksDTOList.stream()
                .map(bookDTO -> EntityModel.of(bookDTO,
                        linkTo(methodOn(BookController.class).getBooksByIsbn(bookDTO.getIsbn())).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksDTOEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }

//    EntityModel<BookDTO>
//    ResponseEntity<BookDTO>
    // return the books based on ISBN
//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value="/books/{isbn}")
    public EntityModel<BookDTO> getBooksByIsbn(@PathVariable(name="isbn")String isbn)
    {

//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Access-Control-Allow-Origin", "*");

//        return ResponseEntity.ok()
//                .headers(responseHeaders)
//                .body(bookService.getBooksByIsbn(isbn));


//        return ResponseEntity.ok()
//                .body(bookService.getBooksByIsbn(isbn));


        return EntityModel.of(bookService.getBooksByIsbn(isbn),
                linkTo(methodOn(BookController.class).getBooksByIsbn(isbn)).withSelfRel());


//        return EntityModel.of(bookService.getBooksByIsbn(isbn),
//                linkTo(methodOn(BookController.class).getBooksByIsbn(isbn)).withSelfRel(),
//                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    //  return the books based on page and items per page (pagination)
    @GetMapping(value="/books", params={"page", "items_per_page"})
    public CollectionModel<EntityModel<BookDTO>> getBooksPerPage(@RequestParam(name="page") int page,
                                                @RequestParam(name="items_per_page") int items_per_page) {
        List<BookDTO> booksDTOList = bookService.getBooksPerPage(page, items_per_page);

        List<EntityModel<BookDTO>> booksEntity = booksDTOList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksPerPage(page, items_per_page)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }

    //@GetMapping("/books")
    // //  return the books based on page and with a <default> items per page
    @GetMapping(value="/books", params="page")
    public CollectionModel<EntityModel<BookDTO>> getBooksPerPageWithItemsByDefault(@RequestParam(name="page") int page){

        int default_items_per_page = 2;
        List<BookDTO> booksDTOList = bookService.getBooksPerPage(page, default_items_per_page);

        List<EntityModel<BookDTO>> booksEntity = booksDTOList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksPerPageWithItemsByDefault(page)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // return the books based on genre
    @GetMapping(value="/books", params="genre")
    public CollectionModel<EntityModel<BookDTO>> getBooksByGenre(@RequestParam(name="genre") String genre){

        List<BookDTO> booksDTOList = bookService.getBooksByGenre(genre);

        List<EntityModel<BookDTO>> booksEntity = booksDTOList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksByGenre(genre)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // return the books based on year
    @GetMapping(value="/books", params="year")
    public CollectionModel<EntityModel<BookDTO>> getBooksByYear(@RequestParam(name="year") Integer year){
        List<BookDTO> booksDTOList = bookService.getBooksByYear(year);

        List<EntityModel<BookDTO>> booksEntity = booksDTOList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksByYear(year)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // return the books based on genre and year
    @GetMapping(value="/books", params={"genre", "year"})
    public CollectionModel<EntityModel<BookDTO>> getBooksByGenreAndYear(@RequestParam(name="year") Integer year,
                                            @RequestParam(name="genre")String genre){

        List<BookDTO> booksDTOList = bookService.getBooksByGenreAndYear(year, genre);

        List<EntityModel<BookDTO>> booksEntity = booksDTOList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBooksByGenreAndYear(year, genre)).withSelfRel(),
                        linkTo(methodOn(BookController.class).getBooks()).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(booksEntity,
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }


    // return a book based on ISBN but with just some of the details(ISBN, title, genre), not all of them
    @GetMapping(value="/books/{isbn}", params="verbose")
    public EntityModel<BookReduceInfoDTO> getBooksByIsbnVerboseFalse(@PathVariable(name="isbn")String isbn,
                                                                     @RequestParam(name="verbose") String verbose)
    {
        return EntityModel.of(bookService.getBooksByIsbnVerboseFalse(isbn),
                linkTo(methodOn(BookController.class).getBooksByIsbn(isbn)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
    }



    // -------------------------------------------------------------------------- POST -----------------------------

    // add a book in database with all the field completed
    @PostMapping(value="/addBook")
    public ResponseEntity<?> registerNewBook(@RequestBody BookDTO bookDTO){
        BookDTO bookSaved = bookService.addNewBook(bookDTO);

        EntityModel<BookDTO> entityModel = EntityModel.of(bookSaved,
                linkTo(methodOn(BookController.class).getBooksByIsbn(bookSaved.getIsbn())).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    // verify the stock for a given book, return the available stock for the book if the request is grater than the stock,
    // or -1, if the requested quantity is lower than the stock available
    @PostMapping(value="/bookCheckStock")
    public String verifyOneBookStock(@RequestBody String bookString){
        JSONObject bookJSONObject = new JSONObject(bookString);
        //System.out.println("----request: " + bookString);
        String availableStock = bookService.checkBookStock(bookJSONObject);
        //System.out.println("----response: " + availableStock);
        return availableStock;
    }


    // verify the available stock for the last time, and update the stock in database if the stock is available, or return the available quantity if not
    @PostMapping(value="/checkFinishOrder")
    public ResponseEntity<?> checkFinishOrder(@RequestBody String bookJSONList){
        JSONObject bookJSONObjects = new JSONObject(bookJSONList);
        return bookService.checkBookStockAll(bookJSONObjects);
    }



}
