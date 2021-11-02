package com.example.pos.Controller;


import com.example.pos.Model.Author.Author;
import com.example.pos.Service.AuthorService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path="api/bookcollection")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }


    @GetMapping("/authors")
    public CollectionModel<Author> getAuthors(){
        return CollectionModel.of(authorService.getAuthors(),
                linkTo(methodOn(AuthorService.class).getAuthors()).withSelfRel(),
                linkTo(methodOn(AuthorService.class).getAuthors()).withRel("bookcollection"));
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


}
