package com.example.bookorder.Controller;


import com.example.bookorder.Model.Book;
import com.example.bookorder.Model.Order;

import com.example.bookorder.Model.OrderStatus;
import com.example.bookorder.Service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.json.JSONObject;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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
    //private final RestTemplate restTemplate;


//    public OrderController(OrderService orderService) {
//        this.orderService = orderService;
//    }


    // ---------------------------------------------  GET  ----------------------------------------

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

    @GetMapping(value="/order/{clientId}")
    public List<Order> getOrdersByClient(@PathVariable(name="clientId") String clientId){

        return orderService.getOrdersByClientId(clientId);
    }



    // ---------------------------------------------  POST  ----------------------------------------

    // add an order based on a client id
    @PostMapping(value="/addOrder/{clientId}")
    public void addOrder(@RequestBody Order order,
                         @PathVariable(name="clientId") String clientId){
        orderService.addOrder(order, clientId);
    }


    // add a book to an order(if exist ? append: create)
    @PostMapping(value="/addBook/{clientId}")
    public String addBookByClientId(@RequestBody Book book,
                                  @PathVariable(name="clientId") String clientId){

        RestTemplate restTemplate = new RestTemplate();

        // impachetez id-ul cartii si cantitatea intr-un json
        JSONObject bookJSONObject =  new JSONObject();
        bookJSONObject.put(book.getIsbn(), book.getQuantity());     // create a json object like { "ISBN-1":"2"}

        String endpointPath = "http://localhost:8080/api/bookcollection/bookCheckStock";

        // the request on endpoint return a JSON which is in a String format
        String resultBookQuantity = restTemplate.postForObject(endpointPath, bookJSONObject.toString(), String.class);

        bookJSONObject = orderService.addBookByClientId(book, clientId, resultBookQuantity);

        return bookJSONObject.toString();
    }


    // communicate with the bookService and FINISH the order if stock is available, or return the available stock if not
    @PostMapping(value="/finishOrder/{clientId}")
    public ResponseEntity<String> finishOrderByClientId(@PathVariable(name="clientId") String clientId){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;
        String endpointPath = "http://localhost:8080/api/bookcollection/checkFinishOrder";

        // return the last order open, which has the status on PENDING
        JSONObject bookJSONList = orderService.getOrderInPendingByClientId(clientId);

        try {
            responseEntity = restTemplate.postForEntity(endpointPath, bookJSONList.toString(), String.class);

            // if there is available stock for every book, the status of the order is set on FINISHED
            if(responseEntity.getStatusCode() == HttpStatus.OK){
                orderService.changeOrderStatusToFinished(clientId);
            }
            return responseEntity;
        }
        catch(HttpClientErrorException e){      // return the available stock, in order to be requested again with the correct value
            return new ResponseEntity<String>(
                    e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST
            );
        }
    }



    // change the order status based on clienId and orderId
    @PutMapping(value="/changeOrderStatus", params={"clientId", "orderId", "orderStatus"})
    public void changeOrderStatus(@RequestParam(name="clientId") String clientId,
                                  @RequestParam(name="orderId") String orderId,
                                  @RequestParam(name="orderStatus") OrderStatus orderStatus){
        orderService.changeOrderStatus(clientId, orderId, orderStatus);
    }



    // ---------------------------------------------  DELETE  ----------------------------------------

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
