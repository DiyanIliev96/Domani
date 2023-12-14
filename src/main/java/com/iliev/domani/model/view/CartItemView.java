package com.iliev.domani.model.view;

import java.math.BigDecimal;

public class CartItemView {

    private Long cartItemId;

    private Long userId;

    private Long productId;

    private String productName;

    private BigDecimal productPrice;

    private String productImageUrl;

    private int quantity;

    private BigDecimal totalPrice;

    public Long getCartItemId() {
        return cartItemId;
    }

    public CartItemView setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public CartItemView setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public CartItemView setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public CartItemView setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public CartItemView setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public CartItemView setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItemView setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public CartItemView setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}
