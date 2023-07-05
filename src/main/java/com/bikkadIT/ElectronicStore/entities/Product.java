package com.bikkadIT.ElectronicStore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Products")
public class Product {

    @Id
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

}
