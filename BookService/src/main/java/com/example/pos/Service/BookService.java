package com.example.pos.Service;

import com.example.pos.Model.Author.AuthorRepository;
import com.example.pos.Model.Book.Book;
import com.example.pos.Model.Book.BookRepository;
import com.example.pos.Model.BookReduceInfo.BookReduceInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Boolean existsTitle = bookRepository.existsByTitle(book.getTitle());
        if(existsTitle) {
            throw new IllegalStateException("Title is not unique!");
        }
        return bookRepository.save(book);
    }


    public List<Book> getBooksPerPage(int page, int items_per_page){
        Pageable paging = PageRequest.of(page, items_per_page);
        Page<Book> pageResult = bookRepository.findAll(paging);

        return pageResult.toList();
    }

    public List<Book> getBooksByGenre(String genre){
        Optional<List<Book>> bookOptional = Optional.of(bookRepository.findByGenre(genre));
        if(bookOptional.isPresent())
        {
            return bookOptional.get();
        }
        else{
            throw new IllegalStateException("No book with this genre!");
        }
    }

    public List<Book> getBooksByYear(Integer year){
        Optional<List<Book>> bookOptional = Optional.of(bookRepository.findByRelease_year(year));
        if(bookOptional.isPresent())
        {
            return bookOptional.get();
        }
        else{
            throw new IllegalStateException("No book with this release year!");
        }
    }

    public List<Book> getBooksByGenreAndYear(String genre, Integer year){
        Optional<List<Book>> bookOptional = Optional.of(bookRepository.findByGenreAndYear(genre, year));
        return bookOptional.get();
    }

    public BookReduceInfo getBooksByIsbnVerboseFalse(String isbn){
        Optional<Book> bookOptional = Optional.of(bookRepository.findByIsbnVerboseFalse(isbn));
        BookReduceInfo bookReduceInfo = new BookReduceInfo(bookOptional.get().getIsbn(),
                bookOptional.get().getTitle(),
                bookOptional.get().getGenre());

        return bookReduceInfo;
    }





}
