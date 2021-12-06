package com.example.bookorder.Controller;


import com.example.bookorder.Model.Order;

import com.example.bookorder.Model.OrderStatus;
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


    // unused (to be deleted)
    @GetMapping(value="/orders")
    public List<Order> getOrders(){
        return orderService.getOrders();
    }


    // return all the orders from a client
    @GetMapping(value="/orders/{clientId}")
    public List<Order> getOrdersByClientId(@PathVariable(name="clientId") String clientId){
        return orderService.getOrdersByClientId(clientId);
    }


    // add an order based on a client id
    @PostMapping(value="/addOrder/{clientId}")
    public void addOrder(@RequestBody Order order,
                         @PathVariable(name="clientId") String clientId){
        orderService.addOrder(order, clientId);
    }


    // change the order status based on clienId and orderId
    @PutMapping(value="/changeOrderStatus", params={"clientId", "orderId", "orderStatus"})
    public void changeOrderStatus(@RequestParam(name="clientId") String clientId,
                                  @RequestParam(name="orderId") String orderId,
                                  @RequestParam(name="orderStatus") OrderStatus orderStatus){
        orderService.changeOrderStatus(clientId, orderId, orderStatus);
    }


    // delete all the orders for a client
    @DeleteMapping(value="/deleteOrdersByClientId/{clientId}")
    public void deleteOrdersByClientId(@PathVariable(name="clientId") String clientId){
        orderService.deleteOrdersByClientId(clientId);
    }


    // delete an order based on a clientId and an orderId
    @DeleteMapping(value="/deleteOrderByOrderId", params={"clientId", "orderId"})
    public void deleteOrdersByOrderId(@RequestParam(name="clientId") String clientId,
                                      @RequestParam(name="orderId") String orderId){
        orderService.deleteOrdersByOrderId(clientId, orderId);
    }




}
