package com.bikkadIT.ElectronicStore.dtos;

import com.bikkadIT.ElectronicStore.entities.OrderItem;
import com.bikkadIT.ElectronicStore.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderDto {

    private String orderId;

    private String orderStatus="PENDING";

    private String paymentStatus="NOTPAID";

    private Integer orderAmount;

    private String billingAddress;

    private String phoneNumber;

    private Date orderDate=new Date();

    private Date deliveredDate;

   // private UserDto user;
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
