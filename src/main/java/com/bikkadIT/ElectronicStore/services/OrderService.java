package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.OrderDto;
import com.bikkadIT.ElectronicStore.entities.Order;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;

import java.util.List;

public interface OrderService {

    // create Order

    OrderDto createOrder(OrderDto orderDto,String userId);

    // Remove Order
    void removeOrder(String orderId);

    // get orders of User
    List<OrderDto> getOrdersOfUser(String userId);

    // get orders
    PageableResponse<OrderDto> getAllOrders(int pageNumber,int pagSize,String sortBy,String sortDir);

    // order methods(logic) related to orders

}
