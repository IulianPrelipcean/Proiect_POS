package com.example.pos.Service;


import com.example.pos.Model.Author.Author;
import com.example.pos.Model.Author.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors(){
        Optional<List<Author>> optionalAuthors = Optional.of(authorRepository.findAll());
        if(optionalAuthors.isPresent())
        {
            return optionalAuthors.get();
        }
        else
        {
            throw new IllegalStateException("There is no authors!");
        }

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

    public Author addNewAuthor(Author author){
        return authorRepository.save(author);
    }

    public List<Author> getAuthorPartialMatch(String first_name){
        Optional<List<Author>> authorPartialMatch = Optional.of(authorRepository.findByNamePartialMatch(first_name));
        if(authorPartialMatch.isPresent())
        {
            return authorPartialMatch.get();
        }
        else
            throw new IllegalStateException("No match found for this name! ");
    }

    public List<Author> getAuthorExactMatch(String first_name){
        Optional<List<Author>> authorExactMatch = Optional.of(authorRepository.findByNameExactMatch(first_name));
        return authorExactMatch.get();
    }




}
