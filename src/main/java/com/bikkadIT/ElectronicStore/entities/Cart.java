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
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    private String cartId;
    private Date createdAt;

    @OneToOne
    private User user;

    //mapping cart-items
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CartItem> item=new ArrayList<>();
}
