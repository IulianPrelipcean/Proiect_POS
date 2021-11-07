package com.example.pos.Controller;


import com.example.pos.Model.Author.Author;
import com.example.pos.Model.Book.Book;
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


    //@GetMapping("/authors")
    @RequestMapping("/authors")
    public CollectionModel<EntityModel<Author>> getAuthors(){

        List<Author> authorsList = authorService.getAuthors();
        List<EntityModel<Author>> authorEntity = authorsList.stream()
                .map(author -> EntityModel.of(author,
                        linkTo(methodOn(AuthorController.class).getAuthorById(author.getId())).withSelfRel(),
                        linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection")))
                .collect(Collectors.toList());

        return CollectionModel.of(authorEntity,
                linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection"));
    }


    @GetMapping("/authors/{id}")
    public EntityModel<Author> getAuthorById(@PathVariable(name="id")Long id){
        return EntityModel.of(authorService.getAuthor(id),
                linkTo(methodOn(AuthorController.class).getAuthorById(id)).withSelfRel(),
                linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection"));
    }

    @PostMapping("/addAuthor")
    public ResponseEntity<?> registerNewAuthor(@RequestBody Author author){
        Author authorSaved = authorService.addNewAuthor(author);

        EntityModel<Author> entityModel = EntityModel.of(authorSaved,
                linkTo(methodOn(AuthorController.class).getAuthorById(authorSaved.getId())).withSelfRel(),
                linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection"));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @RequestMapping(value="/authors", params="name", method=RequestMethod.GET)
    public CollectionModel<EntityModel<Author>> getAuthorPartialMatch(@RequestParam(name="name") String first_name){
        List<Author> authorsList = authorService.getAuthorPartialMatch(first_name);

        List<EntityModel<Author>> authorEntity = authorsList.stream()
                .map(author -> EntityModel.of(author,
                        linkTo(methodOn(AuthorController.class).getAuthorPartialMatch(first_name)).withSelfRel(),
                        linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection")))
                .collect(Collectors.toList());

        return CollectionModel.of(authorEntity,
                linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection"));
    }


    @RequestMapping(value="/authors", params={"name", "match"}, method=RequestMethod.GET)
    public CollectionModel<EntityModel<Author>> getAuthorExactMatch(@RequestParam(name="name") String first_name,
                                                         @RequestParam(name="match") String match){
        List<Author> authorsList = authorService.getAuthorExactMatch(first_name);

        List<EntityModel<Author>> authorEntity = authorsList.stream()
                .map(author -> EntityModel.of(author,
                        linkTo(methodOn(AuthorController.class).getAuthorExactMatch(first_name, match)).withSelfRel(),
                        linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection")))
                .collect(Collectors.toList());

        return CollectionModel.of(authorEntity,
                linkTo(methodOn(AuthorController.class).getAuthors()).withRel("bookcollection"));
    }


}
