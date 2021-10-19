package com.example.demo.Controller;

import com.example.demo.Beans.Book;
import com.example.demo.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/collection")
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

//    @PostMapping("/collection/{name}")
//    public Book addBook(@RequestBody Book book){
//        bookService.saveData(book);
//    }

}
