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
@RequestMapping("/cart")
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




}
