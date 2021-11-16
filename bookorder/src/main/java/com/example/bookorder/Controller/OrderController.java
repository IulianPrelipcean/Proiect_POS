package com.example.bookorder.Controller;


import com.example.bookorder.Model.Customer;
import com.example.bookorder.Service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path="api/bookcollection")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @RequestMapping(value="/books/{isbn}", method= RequestMethod.GET)
    public EntityModel<Customer> getBooksByIsbn(@PathVariable(name="isbn")String isbn)
    {
        return EntityModel.of(orderService.getCustomer(),
                linkTo(methodOn(OrderController.class).getBooksByIsbn(isbn)).withSelfRel());
    }



}
