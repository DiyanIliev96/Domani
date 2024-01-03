package com.iliev.domani.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private ProductEntity product;
    @Column
    private int quantity;
    @Column
    private BigDecimal totalPrice;
    @ManyToOne
    private OrderEntity order;

    public CartItem() {
        this.totalPrice = this.setTotalPrice().getTotalPrice();
    }

    public Long getId() {
        return id;
    }

    public CartItem setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public CartItem setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public CartItem setTotalPrice() {
        if (getProduct() == null) {
            return this;
        }
        this.totalPrice = this.getProduct().getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
        return this;
    }


    public UserEntity getUser() {
        return user;
    }

    public CartItem setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public CartItem setOrder(OrderEntity order) {
        this.order = order;
        return this;
    }
}
