package com.iliev.domani.model.view;

public class OrderView {

    private Long orderId;

    private String userEmail;

    private Long userId;

    private String orderDateTime;

    private String deliveryAddress;

    private String phoneNumber;

    public OrderView() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderView setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderView setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public OrderView setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public OrderView setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
        return this;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderView setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public OrderView setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

}
