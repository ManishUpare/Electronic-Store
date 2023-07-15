package com.bikkadIT.ElectronicStore.dtos;

import com.bikkadIT.ElectronicStore.entities.Cart;
import com.bikkadIT.ElectronicStore.entities.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private Integer cartItemId;

    private Integer quantity;

    private Integer totalPrice;

    private ProductDto productDto;


}
