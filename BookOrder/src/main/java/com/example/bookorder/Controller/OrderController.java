package com.example.bookorder.Controller;


import com.example.bookorder.Model.Order;

import com.example.bookorder.Model.OrderStatus;
import com.example.bookorder.Service.OrderService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;
import java.util.Set;

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

        // TODO -- de mutat in addOrder
        RestTemplate restTemplate = new RestTemplate();
        String endpointPath = "http://localhost:8080/api/bookcollection/bookQuantity";
//        String endpointPath = "http://localhost:8081/api/bookorders/order/1";

        // preiau string-ul cu format json
        String resultBookQuantity = restTemplate.getForObject(endpointPath, String.class);
        //System.out.println("\n\n---- result string is :  " + resultBookQuantity);

        // trec string ul in json
        JSONObject object = new JSONObject(resultBookQuantity);
        //Integer obj = object.getInt("ISBN-7");

        // preiau toate cheile
        Set<String> keys = object.keySet();
        System.out.println("\n\n---- result set keys:  " + keys);

        //System.out.println("\n\n---- result json:  " + obj);

        return orderService.getOrdersByClientId(clientId);
    }

    @GetMapping(value="/order/{clientId}")
    public List<Order> getOrdersByClient(@PathVariable(name="clientId") String clientId){

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
