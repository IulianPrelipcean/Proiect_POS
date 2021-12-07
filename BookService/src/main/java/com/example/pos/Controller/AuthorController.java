package com.example.pos.Controller;


import com.example.pos.Model.DTO.AuthorDTO;
import com.example.pos.Model.Entities.Author.Author;
import com.example.pos.Service.AuthorService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path="api/bookcollection")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }



    // return all the authors
    @GetMapping("/authors")
    public CollectionModel<EntityModel<AuthorDTO>> getAuthors(){

        List<AuthorDTO> authorsDTOList = authorService.getAuthors();
        List<EntityModel<AuthorDTO>> authorEntity = authorsDTOList.stream()
                .map(author -> EntityModel.of(author,
                        linkTo(methodOn(AuthorController.class).getAuthorById(author.getId())).withSelfRel(),
                        linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection")))
                .collect(Collectors.toList());

        return CollectionModel.of(authorEntity,
                linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection"));
    }


    // return the author base on ID
    @GetMapping("/authors/{id}")
    public EntityModel<AuthorDTO> getAuthorById(@PathVariable(name="id")Long id){
        return EntityModel.of(authorService.getAuthor(id),
                linkTo(methodOn(AuthorController.class).getAuthorById(id)).withSelfRel(),
                linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection"));
    }


    // return an author based on a partially matching on its name
    @GetMapping(value="/authors", params="name")
    public CollectionModel<EntityModel<AuthorDTO>> getAuthorPartialMatch(@RequestParam(name="name") String first_name){
        List<AuthorDTO> authorsDTOList = authorService.getAuthorPartialMatch(first_name);

        List<EntityModel<AuthorDTO>> authorEntity = authorsDTOList.stream()
                .map(author -> EntityModel.of(author,
                        linkTo(methodOn(AuthorController.class).getAuthorPartialMatch(first_name)).withSelfRel(),
                        linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection")))
                .collect(Collectors.toList());

        return CollectionModel.of(authorEntity,
                linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection"));
    }


    // return an author based on an exact matching on its name
    @GetMapping(value="/authors", params={"name", "match"})
    public CollectionModel<EntityModel<AuthorDTO>> getAuthorExactMatch(@RequestParam(name="name") String first_name,
                                                                    @RequestParam(name="match") String match){
        List<AuthorDTO> authorsDTOList = authorService.getAuthorExactMatch(first_name);

        List<EntityModel<AuthorDTO>> authorEntity = authorsDTOList.stream()
                .map(author -> EntityModel.of(author,
                        linkTo(methodOn(AuthorController.class).getAuthorExactMatch(first_name, match)).withSelfRel(),
                        linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection")))
                .collect(Collectors.toList());

        return CollectionModel.of(authorEntity,
                linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection"));
    }



    // add a new author
    @PostMapping("/addAuthor")
    public ResponseEntity<?> registerNewAuthor(@RequestBody AuthorDTO authorDTO){
        AuthorDTO authorDTOSaved = authorService.addNewAuthor(authorDTO);

        EntityModel<AuthorDTO> entityModel = EntityModel.of(authorDTOSaved,
                linkTo(methodOn(AuthorController.class).getAuthorById(authorDTOSaved.getId())).withSelfRel(),
                linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection"));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }




}
