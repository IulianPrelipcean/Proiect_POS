package com.example.pos.Service;


import com.example.pos.Model.DTO.AuthorDTO;
import com.example.pos.Model.Entities.Author.Author;
import com.example.pos.Model.Entities.Author.AuthorRepository;
import com.example.pos.Model.Mappers.AuthorMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }


    public List<AuthorDTO> getAuthors(){
        Optional<List<Author>> optionalAuthors = Optional.of(authorRepository.findAll());
        if(optionalAuthors.isPresent())
        {
            List<Author> authorList = optionalAuthors.get();
            return AuthorMapper.authorToAuthorDTOList(authorList);
        }
        else
        {
            throw new IllegalStateException("There is no authors!");
        }

    }


    public AuthorDTO getAuthor(Long ID){
        Optional<Author> authorOption = authorRepository.findById(ID);

        if(authorOption.isPresent()){

            Author author = authorOption.get();
            return AuthorMapper.authorToAuthorDTO(author);
        }
        else
        {
            throw new IllegalStateException("Author with id: " + ID + " doesn't exists!");
        }
    }


    public List<AuthorDTO> getAuthorPartialMatch(String first_name){
        Optional<List<Author>> authorPartialMatch = Optional.of(authorRepository.findByNamePartialMatch(first_name));
        if(authorPartialMatch.isPresent())
        {
            List<Author> authorList = authorPartialMatch.get();
            return AuthorMapper.authorToAuthorDTOList(authorList);
        }
        else
            throw new IllegalStateException("No match found for this name! ");
    }

    public List<AuthorDTO> getAuthorExactMatch(String first_name){
        Optional<List<Author>> authorExactMatch = Optional.of(authorRepository.findByNameExactMatch(first_name));
        List<Author> authorList = authorExactMatch.get();
        return AuthorMapper.authorToAuthorDTOList(authorList);
    }


    public AuthorDTO addNewAuthor(AuthorDTO authorDTO){
        Author author = AuthorMapper.authorDTOToAuthor(authorDTO);
        return AuthorMapper.authorToAuthorDTO(authorRepository.save(author));
    }


}
