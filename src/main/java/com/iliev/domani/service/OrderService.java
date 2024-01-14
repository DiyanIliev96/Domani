package com.iliev.domani.service;

import com.iliev.domani.exception.ObjectNotFoundException;
import com.iliev.domani.model.dto.OrderDto;
import com.iliev.domani.model.entity.CartItem;
import com.iliev.domani.model.entity.OrderEntity;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.model.enums.OrderStatusName;
import com.iliev.domani.repository.CartItemRepository;
import com.iliev.domani.repository.OrderRepository;
import com.iliev.domani.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private final CartItemRepository cartItemRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void makeOrder(OrderDto orderDto, Long userId) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User with id " + userId + " not found.Exception in makeOrder method."));
        OrderEntity newOrder = new OrderEntity()
                .setOrderDateTime(LocalDateTime.now())
                .setUser(user)
                .setDeliveryAddress(orderDto.getDeliveryAddress())
                .setPhoneNumber(orderDto.getPhoneNumber().toString())
                .setAdditionalInfo(orderDto.getAdditionalInfo())
                .setOrderStatus(OrderStatusName.PENDING);

        orderRepository.save(newOrder);

        List<CartItem> userCartItems = cartItemRepository.findAllByUser_idAndOrderIsNull(userId);

        userCartItems.forEach(cartItem -> cartItem.setOrder(newOrder));
        cartItemRepository.saveAll(userCartItems);
    }
}
