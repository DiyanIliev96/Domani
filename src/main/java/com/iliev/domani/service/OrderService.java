package com.iliev.domani.service;

import com.iliev.domani.exception.ObjectNotFoundException;
import com.iliev.domani.model.dto.OrderDto;
import com.iliev.domani.model.entity.CartItem;
import com.iliev.domani.model.entity.OrderEntity;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.model.enums.OrderStatusName;
import com.iliev.domani.model.view.CartItemView;
import com.iliev.domani.model.view.OrderView;
import com.iliev.domani.repository.CartItemRepository;
import com.iliev.domani.repository.OrderRepository;
import com.iliev.domani.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private final CartItemRepository cartItemRepository;

    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartItemRepository cartItemRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.modelMapper = modelMapper;
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
                .setOrderStatus(OrderStatusName.PENDING)
                .setPaymentMethod(orderDto.getPaymentMethod())
                .setPaymentStatus(orderDto.getPaymentStatus());

        orderRepository.save(newOrder);

        List<CartItem> userCartItems = cartItemRepository.findAllByUser_idAndOrderIsNull(userId);

        userCartItems.forEach(cartItem -> cartItem.setOrder(newOrder));
        cartItemRepository.saveAll(userCartItems);
    }

    public List<OrderView> getAllOrdersStatusPending() {

        List<OrderEntity> allByOrderStatus = orderRepository.findAllByOrderStatusOrderByOrderDateTimeDesc(OrderStatusName.PENDING);
        List<OrderView> orderViews = allByOrderStatus.stream()
                .map(order -> modelMapper.map(order, OrderView.class))
                .toList();

        orderViews.forEach(orderView -> {
            String substringDate = orderView.getOrderDateTime().substring(0,orderView.getOrderDateTime().indexOf("."));
            String viewDate = substringDate.replace("T", " at ");
            orderView.setOrderDateTime(viewDate);
        });

        return orderViews;
    }
    @Transactional
    public void deleteOrder(Long id, Long userId) {
        List<CartItem> allByOrderId = cartItemRepository.findAllByOrder_Id(id);
        allByOrderId.forEach(cartItem -> cartItem.setOrder(null));
        cartItemRepository.saveAll(allByOrderId);
        cartItemRepository.deleteAllByUser_Id(userId);
        orderRepository.deleteById(id);
    }

    public void approveOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException("Order with id " + orderId +
                        " not found.Error in approveOrder method."));
        order.setOrderStatus(OrderStatusName.APPROVED);
        orderRepository.save(order);
    }

    public List<CartItemView> getOrderItemsDetails(Long orderId) {
       return cartItemRepository.findAllByOrder_Id(orderId)
               .stream()
               .map(cartItem -> modelMapper.map(cartItem, CartItemView.class))
               .toList();
    }

    public List<OrderEntity> getAllOrdersByUserId(Long id) {
        return orderRepository.findAllByUser_Id(id);
    }
}
