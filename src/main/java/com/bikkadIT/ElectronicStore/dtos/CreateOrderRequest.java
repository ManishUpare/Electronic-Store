package com.bikkadIT.ElectronicStore.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {

    private String cartId;
    private String userId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private String billingAddress;
    private String phoneNumber;
    private String billingName;


}
