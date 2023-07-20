package com.bikkadIT.ElectronicStore.services;

import com.bikkadIT.ElectronicStore.dtos.AddItemToCartRequest;
import com.bikkadIT.ElectronicStore.dtos.CartDto;

public interface CartService {

    // Add items to Cart
    // Case 1: If cart for User is not available then we will create the cart and add the ITEMs
    // Case 2: If Cart available then add item to cart
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from Cart
    void removeItemFromCart(String userId,int cartItem);


    //remove all items from the cart
    void clearCart(String userId);

}
