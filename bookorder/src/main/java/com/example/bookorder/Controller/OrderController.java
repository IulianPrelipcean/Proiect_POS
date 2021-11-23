package com.example.bookorder.Controller;


import com.example.bookorder.Model.Order;

import com.example.bookorder.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

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



    @RequestMapping(value="/orders", method= RequestMethod.GET)
    public List<Order> getOrders(){
        return orderService.getOrders();
    }

//    @GetMapping(value="/orders/{clientId}")
//    public Order getOrdersById(@PathVariable(name="clientId") String id){
//        return orderService.getOrderById(id);
//    }

    @GetMapping(value="/order/{orderId}")
    public Order getOrdersById(@PathVariable(name="orderId") String id){
        return orderService.getOrderById(id);
    }

    @PostMapping(value="/addOrder")
    public void addOrder(@RequestBody Order order){
        orderService.addOrder(order);
    }

    @DeleteMapping(value="/deleteOrders")
    public void deleteOrders(){
        orderService.deleteOrders();
    }



//    @PathVariable(name="isbn")String isbn

}
