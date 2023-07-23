package com.bikkadIT.ElectronicStore.services.Impl;

import com.bikkadIT.ElectronicStore.dtos.CreateOrderRequest;
import com.bikkadIT.ElectronicStore.dtos.OrderDto;
import com.bikkadIT.ElectronicStore.entities.*;
import com.bikkadIT.ElectronicStore.exceptions.BadApiException;
import com.bikkadIT.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadIT.ElectronicStore.helper.AppConstant;
import com.bikkadIT.ElectronicStore.helper.PageableResponse;
import com.bikkadIT.ElectronicStore.repositories.CartRepository;
import com.bikkadIT.ElectronicStore.repositories.OrderRepository;
import com.bikkadIT.ElectronicStore.repositories.UserRepository;
import com.bikkadIT.ElectronicStore.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {

        log.info("Initiating dao call to create New Order");

        String cartId = orderDto.getCartId();
        String userId = orderDto.getUserId();

        //fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + userId));
        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + cartId));

        List<CartItem> cartItems = cart.getItem();   // convert cartItems to OrderItems

        if(cartItems.size() <= 0){
            throw new BadApiException("Invalid Number of Items in Cart");
        }

        Order order = Order.builder()
                .orderId(UUID.randomUUID().toString())
                .billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .orderDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .user(user)
                .build();

        // orderItem, Amount

        // convert cartItems to OrderItems

        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cItem -> {

            // CartItem->OrderItem

          OrderItem orderItem =  OrderItem.builder()
                    .quantity(cItem.getQuantity())
                    .product(cItem.getProduct())
                    .totalPrice(cItem.getQuantity()*cItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

          orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());

            return orderItem;

        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItem().clear();
        cartRepository.save(cart);

        Order savedOrder = orderRepository.save(order);

        log.info("Completed dao call to create New Order");
        return mapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        log.info("Initiating dao call to remove Order");

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND + orderId));
        orderRepository.delete(order);  //if order deleted OrderItem also deleted coz we use cascade.ALL in Order

        log.info("Completed dao call to remove Order");
    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        return null;
    }

    @Override
    public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pagSize, String sortBy, String sortDir) {
        return null;
    }
}
