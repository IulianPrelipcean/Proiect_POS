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
//        Optional<Author> authorOptional = authorRepository.findById(author.getId());
//        if(authorOptional.isPresent()){
//            throw new IllegalStateException("Author with id: " + author.getId() + " already exists!");
//        }

        return authorRepository.save(author);
    }


}
