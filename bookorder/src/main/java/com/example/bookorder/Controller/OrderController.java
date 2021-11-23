package com.example.bookorder.Controller;


import com.example.bookorder.Model.Order;

import com.example.bookorder.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/bookorders")
public class OrderController {

    private final OrderService orderService;


//    public OrderController(OrderService orderService) {
//        this.orderService = orderService;
//    }

//    @RequestMapping(value="/client", method= RequestMethod.GET)
//    public EntityModel<Order> getBooksByIsbn()
//    {
//        return EntityModel.of(orderService.getOrders(),
//                linkTo(methodOn(OrderController.class).getBooksByIsbn()).withSelfRel());
//    }



    @RequestMapping(value="/client", method= RequestMethod.GET)
    public List<Order> getClients(){
        return orderService.getOrders();
    }


//    @PathVariable(name="isbn")String isbn

}
