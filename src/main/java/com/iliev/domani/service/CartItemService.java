package com.iliev.domani.service;

import com.iliev.domani.exception.ObjectNotFoundException;
import com.iliev.domani.model.entity.CartItem;
import com.iliev.domani.model.entity.ProductEntity;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.model.view.CartItemView;
import com.iliev.domani.repository.CartItemRepository;
import com.iliev.domani.repository.ProductRepository;
import com.iliev.domani.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final CartItemRepository cartItemRepository;

    private final ModelMapper modelMapper;

    public CartItemService(ProductRepository productRepository, UserRepository userRepository, CartItemRepository cartItemRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.modelMapper = modelMapper;
    }

    public void addToCart(Long productId, Long id,int quantity) {
        CartItem cartItem = new CartItem();
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User with " + id + " not found.Exception in addToCart method."));
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ObjectNotFoundException
                                ("Product with " + productId + " not found.Exception in addToCart method."));

        cartItem.setProduct(productEntity)
                .setUser(userEntity)
                .setQuantity(quantity)
                        .setTotalPrice();
        cartItemRepository.save(cartItem);
    }

    public List<CartItemView> getItemsByUserId(Long userId) {
        List<CartItemView> cartItemViews = cartItemRepository.findAllByUser_idAndOrderIsNull(userId)
                .stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemView.class))
                .toList();

        return cartItemViews;
    }


    public void deleteCartItem(Long cartItemId) {
        this.cartItemRepository.deleteById(cartItemId);
    }
    @Transactional
    public void deleteAllCartItems(Long userId) {
        cartItemRepository.deleteAllByUser_Id(userId);
    }
}
