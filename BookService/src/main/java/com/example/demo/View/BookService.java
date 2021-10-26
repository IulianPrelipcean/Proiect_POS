package com.example.demo.View;

import com.example.demo.Model.Author.Author;
import com.example.demo.Model.Author.AuthorRepository;
import com.example.demo.Model.Book.Book;
import com.example.demo.Model.Book.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    public List<Book> getBooks(){
        Optional<List<Book>> booksOption = Optional.of(bookRepository.findAll());
        if(!booksOption.isPresent()){
            throw new IllegalStateException("There is no book !");
        }
        else{
            return booksOption.get();
        }
    }


    public Book getBooksByIsbn(String isbn){
        Optional<Book> bookOption = bookRepository.findById(isbn);
//        Book bookOption = bookRepository.findById(ISBN).orElse(null);
//        return bookOption;
        if (bookOption.isPresent()){
            return bookOption.get();
        }
        else
        {
            throw new IllegalStateException("Book with ISBN: " + isbn + " doesn't exists!");
        }
//        String sql = "SELECT * FROM Book";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        //return Book.getBooks();
    }

    public Book addNewBook(Book book){
        Optional<Book> bookOptional = bookRepository.findById(book.getIsbn());
        if(bookOptional.isPresent()) {
            throw new IllegalStateException("Book already exists!");
        }
        return bookRepository.save(book);
        //return book;
    }

    public Author getAuthor(Long ID){
        Optional<Author> authorOption = authorRepository.findById(ID);

        if(authorOption.isPresent()){
            return authorOption.get();
        }
        else
        {
            throw new IllegalStateException("Author with id: " + ID + " doesn't exists!");
        }
    }





}
