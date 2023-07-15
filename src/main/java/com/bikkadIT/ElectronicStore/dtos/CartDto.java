package com.bikkadIT.ElectronicStore.dtos;

import com.bikkadIT.ElectronicStore.entities.CartItem;
import com.bikkadIT.ElectronicStore.entities.User;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private Integer cartId;
    private Date createdAt;

    private UserDto userDto;

    private List<CartItemDto> item = new ArrayList<>();
}
