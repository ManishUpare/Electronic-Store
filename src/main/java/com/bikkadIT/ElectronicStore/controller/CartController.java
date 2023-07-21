package com.bikkadIT.ElectronicStore.controller;

import com.bikkadIT.ElectronicStore.dtos.AddItemToCartRequest;
import com.bikkadIT.ElectronicStore.dtos.CartDto;
import com.bikkadIT.ElectronicStore.payloads.ApiResponse;
import com.bikkadIT.ElectronicStore.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    // add items

    /**
     * @apiNote This method is for Add Item In Cart
     * @param userId
     * @param request
     * @return
     */

    @PostMapping("/add/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request){

        log.info("Entering the request to Add item in cart with userId:{} ", userId);

        CartDto cartDto = cartService.addItemToCart(userId, request);

        log.info("Completed the request to Add item in cart with userId:{} ", userId);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    /**
     * @apiNote This method is for Remove Item from Cart
     * @param userId
     * @param itemId
     * @return
     */

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId,@PathVariable int itemId){

        log.info("Entering the request for remove item from cart with userId:{} itemId :{}", userId,itemId);

        cartService.removeItemFromCart(userId,itemId);
        ApiResponse apiResponse = ApiResponse.builder().success(true).message("Item Removed!!").status(HttpStatus.OK).build();

        log.info("Completed the request for remove item from cart with userId:{} itemId :{} ", userId,itemId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    /**
     * @apiNote This method is for clear cart items
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId){

        log.info("Entering the request for clear cart with userId:{}", userId);

        cartService.clearCart(userId);
        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .message("Cart is Cleared !!")
                .status(HttpStatus.OK)
                .build();

        log.info("Completed the request for clear cart with userId:{}", userId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    /**
     * @apiNote This method is for get Cart By User
     * @param userId
     * @return
     */
    @GetMapping("/get/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId){

        log.info("Entering the request to get Cart By User with userId:{} ", userId);

        CartDto cartDto = cartService.getCartByUser(userId);

        log.info("Completed the request to get Cart By User with userId:{} ", userId);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }


}
