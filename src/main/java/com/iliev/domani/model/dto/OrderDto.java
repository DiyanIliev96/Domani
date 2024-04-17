package com.iliev.domani.model.dto;

import com.iliev.domani.model.enums.PaymentMethodEnum;
import com.iliev.domani.model.enums.PaymentStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

public class OrderDto {
    @NotBlank(message = "Delivery address cannot be empty!")
    private String deliveryAddress;

    @NumberFormat(pattern = "^-?\\d+$")
    @NotNull(message = "Phone number cannot be empty!")
    private Long phoneNumber;
    @Size(max = 500,message = "Additional info characters cannot be more than 500!")
    private String additionalInfo;

    private int orderAmount;


    private String stripeToken;

    private PaymentMethodEnum paymentMethod;

    private PaymentStatusEnum paymentStatus;

    public OrderDto() {
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderDto setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public OrderDto setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public OrderDto setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public OrderDto setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public OrderDto setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
        return this;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public OrderDto setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public OrderDto setPaymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }
}
