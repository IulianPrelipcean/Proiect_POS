package com.example.gateway.Controller;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="gw")
public class ApiBookController {


//    //    EntityModel<BookDTO>
////    ResponseEntity<BookDTO>
//    // return the books based on ISBN
////    @CrossOrigin(origins = "http://localhost:3000")
//    @GetMapping(value="/books/{isbn}")
//    public EntityModel<String> getBooksByIsbn(@PathVariable(name="isbn")String isbn)
//    {
//
////        HttpHeaders responseHeaders = new HttpHeaders();
////        responseHeaders.set("Access-Control-Allow-Origin", "*");
//
////        return ResponseEntity.ok()
////                .headers(responseHeaders)
////                .body(bookService.getBooksByIsbn(isbn));
//
//
////        return ResponseEntity.ok()
////                .body(bookService.getBooksByIsbn(isbn));
//
//
//        return EntityModel.of(bookService.getBooksByIsbn(isbn),
//                linkTo(methodOn(BookController.class).getBooksByIsbn(isbn)).withSelfRel());
//
//
////        return EntityModel.of(bookService.getBooksByIsbn(isbn),
////                linkTo(methodOn(BookController.class).getBooksByIsbn(isbn)).withSelfRel(),
////                linkTo(methodOn(BookController.class).getBooks()).withRel("bookcollection"));
//    }




    @RequestMapping
    public String allRoutes() {
//        ResponseEntity<?> responseEntity =
        return "string";
    }


//    @Bean
//    public RouteLocator myRoute(RouteLocatorBuilder builder){
//        return builder.routes()
//                .route(p -> p
//                .path("/apigate")
//                .uri("http://localhost:8080/api/bookcollection/books"))
//                //.uri("http://httpbin.org:80"))
//                .build();
//
//
//    }



}
