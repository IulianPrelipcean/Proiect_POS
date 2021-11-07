package com.example.pos.Controller;


import com.example.pos.Model.Author.Author;
import com.example.pos.Service.AuthorService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path="api/bookcollection")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }


    @GetMapping("/authorsAll")
    public CollectionModel<Author> getAuthors(){
        return CollectionModel.of(authorService.getAuthors(),
                linkTo(methodOn(AuthorService.class).getAuthors()).withSelfRel(),
                linkTo(AuthorService.class).withRel("bookcollection"));
    }


    @GetMapping("/authors/{id}")
    public EntityModel<Author> getAuthorById(@PathVariable(name="id")Long id){
        return EntityModel.of(authorService.getAuthor(id),
                linkTo(methodOn(AuthorController.class).getAuthorById(id)).withSelfRel(),
                linkTo(AuthorController.class).withRel("bookcollection"));
    }

    @PostMapping("/addAuthor")
    public ResponseEntity<?> registerNewAuthor(@RequestBody Author author){
        Author authorSaved = authorService.addNewAuthor(author);

        EntityModel<Author> entityModel = EntityModel.of(authorSaved,
                linkTo(methodOn(AuthorController.class).getAuthorById(authorSaved.getId())).withSelfRel(),
                linkTo(AuthorController.class).withRel("bookcollection"));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @RequestMapping(value="/authors", params="name", method=RequestMethod.GET)
    public CollectionModel<Author> getAuthorPartialMatch(@RequestParam(name="name") String first_name){
        List<Author> author = authorService.getAuthorPartialMatch(first_name);

        return CollectionModel.of(author,
                linkTo(methodOn(AuthorController.class).getAuthorPartialMatch(first_name)).withSelfRel(),
                linkTo(AuthorController.class).withRel("bookcollection"));
    }


    @RequestMapping(value="/authors", params={"name", "match"}, method=RequestMethod.GET)
    public CollectionModel<Author> getAuthorExactMatch(@RequestParam(name="name") String first_name,
                                                         @RequestParam(name="match") String match){
        List<Author> author = authorService.getAuthorExactMatch(first_name);

        return CollectionModel.of(author,
                linkTo(methodOn(AuthorController.class).getAuthorExactMatch(first_name, match)).withSelfRel(),
                linkTo(AuthorController.class).withRel("bookcollection"));
    }


}
