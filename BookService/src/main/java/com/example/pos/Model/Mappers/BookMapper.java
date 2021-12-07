package com.example.pos.Model.Mappers;

import com.example.pos.Model.DTO.BookDTO;

import com.example.pos.Model.Entities.Book.Book;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookMapper {

    public BookDTO bookToBookDTO(Book book){
        BookDTO bookDTO = BookDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .genre(book.getGenre())
                .publisher(book.getPublisher())
                .release_year(book.getRelease_year())
                .number_of_pages(book.getNumber_of_pages())
                .price(book.getPrice())
                .available_stock(book.getAvailable_stock())
                .build();
        return bookDTO;
    }


    public Book bookDTOToBook(BookDTO bookDTO){
        Book book = Book.builder()
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .genre(bookDTO.getGenre())
                .publisher(bookDTO.getPublisher())
                .release_year(bookDTO.getRelease_year())
                .number_of_pages(bookDTO.getNumber_of_pages())
                .price(bookDTO.getPrice())
                .available_stock(bookDTO.getAvailable_stock())
                .build();
        return book;
    }


    public List<BookDTO> bookToBookDTOList(List<Book> bookList){
        List<BookDTO> bookDTOList = bookList.stream().map(BookMapper::bookToBookDTO).collect(Collectors.toList());
        return bookDTOList;
    }


    public List<Book> bookDTOToBookList(List<BookDTO> bookDTOList){
        List<Book> bookList = bookDTOList.stream().map(BookMapper::bookDTOToBook).collect(Collectors.toList());
        return bookList;
    }

}
