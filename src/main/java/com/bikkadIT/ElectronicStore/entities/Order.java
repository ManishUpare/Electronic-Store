package com.bikkadIT.ElectronicStore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;

    private String billingName;

    // Pending,Dispatched,Delivered
    // we can use ENUM also
    private String orderStatus;

    // NOT PAID , PAID
    // Enum
    // boolean false=NOT Paid, true=Paid
    private String paymentStatus;

    private Integer orderAmount;

   // @Column(length = 1000)
    private String billingAddress;

    private String phoneNumber;

    private Date orderDate;

    private Date deliveredDate;

    // one user has many order
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems=new ArrayList<>();
}
