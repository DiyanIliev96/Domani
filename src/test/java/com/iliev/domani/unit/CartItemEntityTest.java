package com.iliev.domani.unit;

import com.iliev.domani.model.entity.CartItem;
import com.iliev.domani.model.entity.ProductEntity;
import com.iliev.domani.model.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CartItemEntityTest {
    private CartItem cartItem;
    private UserEntity user;
    @BeforeEach
    void setUp () {
        user = new UserEntity()
                .setId(1L);
        ProductEntity product = new ProductEntity()
                .setId(2L)
                .setPrice(BigDecimal.TEN);
         cartItem = new CartItem()
                .setId(3L)
                .setQuantity(2)
                .setProduct(product)
                .setUser(user)
                .setTotalPrice();
    }

    @Test
    void testCartItemTotalPrice () {
        Assertions.assertEquals(BigDecimal.valueOf(20),cartItem.getTotalPrice());
    }

    @Test
    void testCartItemGetId() {
        Assertions.assertEquals(3L,cartItem.getId());
    }

    @Test
    void testCartItemSetId() {
        Assertions.assertEquals(3L,cartItem.getId());
        cartItem.setId(4L);
        Assertions.assertEquals(4L,cartItem.getId());
    }

    @Test
    void testCartItemGetUser() {
        Assertions.assertEquals(cartItem.getUser(),user);
    }
}
