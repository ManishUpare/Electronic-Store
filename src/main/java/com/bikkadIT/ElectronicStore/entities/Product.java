package com.bikkadIT.ElectronicStore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product {

    @Id
    private String productId;

    private String title;

    @Column(length = 10000)
    private String description;
    private Integer price;
    private Integer discountedPrice;
    private Integer quantity;
    private Date addedDate;
    private Boolean isLive;
    private Boolean isStock;

}
