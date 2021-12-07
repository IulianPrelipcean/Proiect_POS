package com.example.pos.Service;


import com.example.pos.Model.Entities.Author.Author;
import com.example.pos.Model.Entities.BookAuthor.BookAuthor;
import com.example.pos.Model.Entities.BookAuthor.BookAuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;

    public BookAuthorService(BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
    }

    public BookAuthor addNewBookAuthor(BookAuthor bookAuthor){
        return bookAuthorRepository.save(bookAuthor);
    }


    public List<Author> getAuthorsForIsbn(String isbn){
    //public void getAuthorsForIsbn(String isbn){
        Optional<List<BookAuthor>> bookAuthorOptional = Optional.of(bookAuthorRepository.findByBook_isbn(isbn));

        List<BookAuthor> bookAuthorsList = bookAuthorOptional.get();
        List<Author> authorsList = new ArrayList<Author>();
        for(BookAuthor bookAuthor : bookAuthorsList)
        {
            authorsList.add(bookAuthor.getAuthor());
            System.out.println("autor==  " +  bookAuthor.getAuthor().getFirst_name());
        }

        return authorsList;

    }


}
