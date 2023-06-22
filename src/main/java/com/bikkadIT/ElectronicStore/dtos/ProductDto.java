package com.bikkadIT.ElectronicStore.dtos;

import com.bikkadIT.ElectronicStore.entities.Category;
import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private String productId;
    private String title;
    private String description;
    private Integer price;
    private Integer discountedPrice;
    private Integer quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;

    private CategoryDto category;


}
