package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.CreateOrderRequest;
import com.bikkadIT.ElectronicStore.dtos.OrderDto;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.payloads.ApiResponse;
import com.bikkadIT.ElectronicStore.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @Author Manish Upare
     * @apiNote This api is for creating orders
     * @param orderDto
     * @return
     */
    //create order
    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest orderDto) {

        log.info("Entering the request for create order details");
        OrderDto order = orderService.createOrder(orderDto);

        log.info("Completed the request for create order details");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * @apiNote This api is to remove orders
     * @param orderId
     * @return
     */

    @DeleteMapping("/remove/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId){
        log.info("Entering the request to remove order details");

        orderService.removeOrder(orderId);

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Order is Removed !!")
                .success(true)
                .build();
        log.info("Completed the request to remove order details");

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    /**
     * @apiNote This api is to get order details By User
     * @param userId
     * @return
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderByUser(@PathVariable String userId){

        log.info("Entering the request to get order details By User");
        List<OrderDto> orders = orderService.getOrdersOfUser(userId);

        log.info("Completed the request to get order details By User");
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    /**
     * @apiNote This method is to get all orders details
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    @GetMapping("/getAllOrders")
    public ResponseEntity <PageableResponse<OrderDto>> getAllOrders(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

        PageableResponse<OrderDto> allOrders = orderService.getAllOrders(pageNumber, pageSize, sortBy, sortBy);

        log.info("Entering the request for get all order data");
        return new ResponseEntity<>(allOrders,HttpStatus.OK);
    }

}
