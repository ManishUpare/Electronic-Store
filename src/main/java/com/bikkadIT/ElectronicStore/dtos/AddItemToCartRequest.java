package com.bikkadIT.ElectronicStore.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemToCartRequest {

    private String productId;

    private int quantity;
}
