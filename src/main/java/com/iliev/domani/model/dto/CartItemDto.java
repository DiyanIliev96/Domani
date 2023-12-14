package com.iliev.domani.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

public class CartItemDto {
    @NumberFormat(pattern = "^-?\\d+$")
    @Min(value = 1,message = "Product id must be higher than 0.")
    private long productId;
    @NumberFormat(pattern = "^-?\\d+$")
    @Min(value = 1,message = "Quantity must be at least 1.")
    @Max(value = 10,message = "Quantity cannot be more than 10.")
    @NotNull(message = "Quantity cannot be null.")
    private int quantity;

    public long getProductId() {
        return productId;
    }

    public CartItemDto setProductId(long productId) {
        this.productId = productId;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItemDto setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
