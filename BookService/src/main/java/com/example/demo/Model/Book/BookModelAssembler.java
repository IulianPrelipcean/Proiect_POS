package com.example.demo.Model.Book;

import com.example.demo.Controller.BookController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

//@Component
//public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {
//    @Override
//    public EntityModel<Book> toModel(Book book){
//        return EntityModel.of(book,
//                linkTo(methodOn(BookController.class)));
//
//    }
//}
