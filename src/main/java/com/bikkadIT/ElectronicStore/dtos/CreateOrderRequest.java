package com.bikkadIT.ElectronicStore.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotBlank(message = "cartId is required")
    private String cartId;

    @NotBlank(message = "userId is required")
    private String userId;

    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";

    @NotBlank(message = "Address is required")
    private String billingAddress;

    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;

    @NotBlank(message = "Billing Name is required")
    private String billingName;


}
