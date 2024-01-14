package com.iliev.domani.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

public class OrderDto {
    @NotBlank(message = "Delivery address cannot be empty!")
    private String deliveryAddress;

    @NumberFormat(pattern = "^-?\\d+$")
    @NotNull(message = "Phone number cannot be null!")
    private Long phoneNumber;
    @Size(max = 500,message = "Additional info characters cannot be more than 500!")
    private String additionalInfo;


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
}
