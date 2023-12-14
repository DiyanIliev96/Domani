package com.iliev.domani.web;

import com.iliev.domani.model.entity.CartItem;
import com.iliev.domani.model.entity.ProductEntity;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.model.enums.CategoryNameEnum;
import com.iliev.domani.repository.*;
import com.iliev.domani.user.DomaniUserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    private CartItem cartItem;
    private UserEntity user;

    private ProductEntity product;

    public static UserDetails testUser() {
        return new DomaniUserDetail(1L,"User Userov","user@user.com",
                "user", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
    @BeforeEach
    void setUp () {
        product = new ProductEntity()
                .setId(1L)
                .setPrice(BigDecimal.TEN)
                .setName("test product")
                .setCategory(categoryRepository.findByName(CategoryNameEnum.Lunch).get())
                .setDescription("test description")
                .setImageUrl("test url");
        productRepository.save(product);
        cartItem = new CartItem()
                .setId(1L)
                .setQuantity(2)
                .setProduct(product)
                .setUser(user)
                .setTotalPrice();

        cartItemRepository.save(cartItem);
    }


    @Test
    void addToCart_successfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add").with(user(testUser()))
                .with(csrf())
                .param("productId","1")
                .param("category","lunch")
                        .param("quantity","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/menu?category=lunch"));
    }

    @Test
    void addToCart_ShouldThrow404_productDoesntExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add").with(user(testUser()))
                        .with(csrf())
                        .param("productId","99")
                        .param("category","lunch")
                        .param("quantity","1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addToCart_ShouldRedirectToMenuPage_withWrongQuantity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add").with(user(testUser()))
                        .with(csrf())
                        .param("productId","1")
                        .param("category","lunch")
                        .param("quantity","0"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/cart-error"));
    }

    @Test
    void deleteFromCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cart/delete/1").with(csrf())
                        .with(user(testUser())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }

    @Test
    void deleteFromCartAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cart/delete/all").with(csrf())
                        .with(user(testUser())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }
}
