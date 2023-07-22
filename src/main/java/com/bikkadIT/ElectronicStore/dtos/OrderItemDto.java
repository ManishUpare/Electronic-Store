package com.bikkadIT.ElectronicStore.dtos;

import com.bikkadIT.ElectronicStore.entities.Order;
import com.bikkadIT.ElectronicStore.entities.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderItemDto {

    private Integer orderItemId;

    private Integer quantity;

    private Integer totalPrice;

    private ProductDto product;

}
