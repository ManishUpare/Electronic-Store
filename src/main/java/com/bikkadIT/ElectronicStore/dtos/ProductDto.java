package com.bikkadIT.ElectronicStore.dtos;

import com.bikkadIT.ElectronicStore.entities.Category;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private String productId;

    @NotBlank
    @Size(min = 4,message = "Title must be of minimum 4 characters")
    private String title;

    @NotBlank(message = "Description is Required !!")
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
