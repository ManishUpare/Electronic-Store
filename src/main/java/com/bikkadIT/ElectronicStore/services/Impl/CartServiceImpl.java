package com.bikkadIT.ElectronicStore.services.Impl;

import com.bikkadIT.ElectronicStore.dtos.AddItemToCartRequest;
import com.bikkadIT.ElectronicStore.dtos.CartDto;
import com.bikkadIT.ElectronicStore.entities.Cart;
import com.bikkadIT.ElectronicStore.entities.CartItem;
import com.bikkadIT.ElectronicStore.entities.Product;
import com.bikkadIT.ElectronicStore.entities.User;
import com.bikkadIT.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.repositories.CartRepository;
import com.bikkadIT.ElectronicStore.repositories.ProductRepository;
import com.bikkadIT.ElectronicStore.repositories.UserRepository;
import com.bikkadIT.ElectronicStore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();
        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + productId));
        //fetch thr user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + userId));

        //If cart for User is not available then we will create the cart and add the ITEMs

        Cart cart =null;

        try {

             cartRepository.findByUser(user).get();

        }catch (NoSuchElementException e){

            cart=new Cart();
            cart.setCreatedAt(new Date());
        }

        //perform cart operation

        //if Cart Item already present, then update
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> item = cart.getItem();
        List<CartItem> updatedItems = item.stream().map(items -> {
            if (items.getProduct().getProductId().equals(productId)) {
                //item already present in cart
                items.setQuantity(quantity);
                items.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }
            return items;
        }).collect(Collectors.toList());

        cart.setItem(updatedItems);

        //   create/Add items

        if(!updated.get()){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItem().add(cartItem);
        }

        cart.setUser(user);

        Cart updatedCart = cartRepository.save(cart);

        return mapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
