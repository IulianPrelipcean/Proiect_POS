package com.example.pos.Model.Mappers;

import com.example.pos.Model.DTO.AuthorDTO;
import com.example.pos.Model.Entities.Author.Author;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AuthorMapper {

    public AuthorDTO authorToAuthorDTO(Author author){
        AuthorDTO authorDTO = AuthorDTO.builder()
                .id(author.getId())
                .first_name(author.getFirst_name())
                .last_name(author.getLast_name())
                .build();
        return authorDTO;
    }


    public Author authorDTOToAuthor(AuthorDTO authorDTO){
        Author author = Author.builder()
                .id(authorDTO.getId())
                .first_name(authorDTO.getFirst_name())
                .last_name(authorDTO.getLast_name())
                .build();
        return author;
    }

    public List<AuthorDTO> authorToAuthorDTOList(List<Author> authorList) {
        List<AuthorDTO> authorDTOList = authorList.stream().map(AuthorMapper::authorToAuthorDTO).collect(Collectors.toList());
        return authorDTOList;
    }

    public List<Author> authorDTOToAuthorList(List<AuthorDTO> authorDTOList) {
        List<Author> authorList = authorDTOList.stream().map(AuthorMapper::authorDTOToAuthor).collect(Collectors.toList());
        return authorList;
    }




}
