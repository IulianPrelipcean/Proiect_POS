package com.example.demo.Services;

import com.example.demo.Beans.Book;
import com.example.demo.Model.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(){
        Optional<List<Book>> bookOption = Optional.ofNullable(bookRepository.findAll());
        System.out.println("carti---------------");
        System.out.println(bookOption.get());
        if (bookOption.isPresent()){
            return bookOption.get();
        }
        else
        {
            throw new RuntimeException();
        }
//        String sql = "SELECT * FROM Book";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        //return Book.getBooks();
    }

    public void saveData(Book book){

    }

}
