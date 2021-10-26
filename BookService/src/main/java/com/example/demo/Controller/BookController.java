package com.example.demo.Controller;

import com.example.demo.Model.Author.Author;
import com.example.demo.Model.Book.Book;
import com.example.demo.View.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/bookcollection")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<Book> getBooks(@PathVariable(name="isbn")String isbn)
    {
        return ResponseEntity.ok(bookService.getBooks(isbn));
    }

    @GetMapping("/authors/{ID}")
    public ResponseEntity<Author> getAuthor(@PathVariable(name="ID")Long ID){
        return ResponseEntity.ok(bookService.getAuthor(ID));
    }

    @PostMapping("/addBook")
    public void registerNewBook(@RequestBody Book book){
        bookService.addNewBook(book);
    }


}
