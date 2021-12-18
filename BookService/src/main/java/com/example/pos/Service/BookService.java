package com.example.pos.Service;

import com.example.pos.Model.DTO.BookDTO;
import com.example.pos.Model.DTO.BookReduceInfoDTO;
import com.example.pos.Model.Entities.Author.AuthorRepository;
import com.example.pos.Model.Entities.Book.Book;
import com.example.pos.Model.Entities.Book.BookRepository;
import com.example.pos.Model.Entities.BookReduceInfo.BookReduceInfo;
import com.example.pos.Model.Mappers.BookMapper;
import com.example.pos.Model.Mappers.BookReduceInfoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    public BookService(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }



    public JSONObject getBooksStock(){
        Optional<List<Book>> bookOption = Optional.of(bookRepository.findAll());
        if(bookOption.isPresent()){
            List<Book> booksList = bookOption.get();
            JSONObject jsonObjectBooks = new JSONObject();
            for(Book book: booksList){
                jsonObjectBooks.put(book.getIsbn(), book.getAvailable_stock());
            }
            return jsonObjectBooks;
        }
        else{
            throw new IllegalStateException("There is no book available!");
        }
    }



    public List<BookDTO> getBooks(){
        Optional<List<Book>> booksOption = Optional.of(bookRepository.findAll());
        if(!booksOption.isPresent()){
            throw new IllegalStateException("There is no book available!");
        }
        else{
            List<Book> bookList = booksOption.get();
            return BookMapper.bookToBookDTOList(bookList);
        }
    }


    public BookDTO getBooksByIsbn(String isbn){
        Optional<Book> bookOption = bookRepository.findById(isbn);
//        Book bookOption = bookRepository.findById(ISBN).orElse(null);
//        return bookOption;
        if (bookOption.isPresent()){
            Book book = bookOption.get();
            return BookMapper.bookToBookDTO(book);
        }
        else
        {
            throw new IllegalStateException("Book with ISBN: " + isbn + " doesn't exists!");
        }
//        String sql = "SELECT * FROM Book";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        //return Book.getBooks();
    }





    public List<BookDTO> getBooksPerPage(int page, int items_per_page){
        Pageable paging = PageRequest.of(page, items_per_page);
        Page<Book> pageResult = bookRepository.findAll(paging);
        List<Book> bookList = pageResult.toList();

        return BookMapper.bookToBookDTOList(bookList);
    }


    public List<BookDTO> getBooksByGenre(String genre){
        Optional<List<Book>> bookOptional = Optional.of(bookRepository.findByGenre(genre));
        if(bookOptional.isPresent())
        {
            List<Book> bookList = bookOptional.get();
            return BookMapper.bookToBookDTOList(bookList);
        }
        else{
            throw new IllegalStateException("No book with this genre!");
        }
    }


    public List<BookDTO> getBooksByYear(Integer year){
        Optional<List<Book>> bookOptional = Optional.of(bookRepository.findByRelease_year(year));
        if(bookOptional.isPresent())
        {
            List<Book> bookList = bookOptional.get();
            return BookMapper.bookToBookDTOList(bookList);
        }
        else{
            throw new IllegalStateException("No book with this release year!");
        }
    }


    public List<BookDTO> getBooksByGenreAndYear(Integer year, String genre){
        Optional<List<Book>> bookOptional = Optional.of(bookRepository.findByGenreAndYear(genre, year));
        List<Book> bookList = bookOptional.get();
        return BookMapper.bookToBookDTOList(bookList);
    }


    public BookReduceInfoDTO getBooksByIsbnVerboseFalse(String isbn){
        Optional<Book> bookOptional = Optional.of(bookRepository.findByIsbnVerboseFalse(isbn));
        BookReduceInfo bookReduceInfo = new BookReduceInfo(
                bookOptional.get().getIsbn(),
                bookOptional.get().getTitle(),
                bookOptional.get().getGenre());

        BookReduceInfoDTO bookReduceInfoDTO = BookReduceInfoMapper.bookReduceToBookReduceDTO(bookReduceInfo);

        return bookReduceInfoDTO;
    }



    // --------------------------------------------------------------- POST ----------------------------------

    public BookDTO addNewBook(BookDTO bookDTO){
        Book book = BookMapper.bookDTOToBook(bookDTO);

        Optional<Book> bookOptional = bookRepository.findById(book.getIsbn());
        if(bookOptional.isPresent()) {
            throw new IllegalStateException("Book already exists!");
        }
        Boolean existsTitle = bookRepository.existsByTitle(book.getTitle());
        if(existsTitle) {
            throw new IllegalStateException("Title is not unique!");
        }
        return BookMapper.bookToBookDTO(bookRepository.save(book));
    }



    public String checkBookStock(JSONObject bookJSONObject){
        Set<String> bookKeys = bookJSONObject.keySet();
        String bookIsbn = "";
        for(String key: bookKeys){
            bookIsbn = key;
        }

        Optional<Book> bookOptional = bookRepository.findById(bookIsbn);
        Book book = bookOptional.get();
        if(bookOptional.isPresent()){
            if(book.getAvailable_stock() > bookJSONObject.getInt(bookIsbn)){
                bookJSONObject.put(bookIsbn, -1);       // stock is available, so we will return an invalid result
            }
            else{
                bookJSONObject.put(bookIsbn, book.getAvailable_stock());      // there is not enough books, so we will return the available stock
            }
        }
        return bookJSONObject.toString();
    }



}
