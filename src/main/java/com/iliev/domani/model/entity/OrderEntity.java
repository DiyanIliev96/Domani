package com.iliev.domani.model.entity;

import com.iliev.domani.model.enums.OrderStatusName;
import com.iliev.domani.model.enums.PaymentMethodEnum;
import com.iliev.domani.model.enums.PaymentStatusEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(optional = false)
    private UserEntity user;
    @Column(nullable = false)
    private LocalDateTime orderDateTime;
    @Enumerated(EnumType.STRING)
    private OrderStatusName orderStatus;
    @Column(nullable = false)
    private String deliveryAddress;
    @Column(columnDefinition = "TEXT")
    private String additionalInfo;
    @Column(nullable = false)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    public OrderEntity() {
    }

    public long getId() {
        return id;
    }

    public OrderEntity setId(long id) {
        this.id = id;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public OrderEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public OrderEntity setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
        return this;
    }

    public OrderStatusName getOrderStatus() {
        return orderStatus;
    }

    public OrderEntity setOrderStatus(OrderStatusName orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderEntity setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public OrderEntity setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public OrderEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public OrderEntity setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public OrderEntity setPaymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }
}
