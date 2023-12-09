package com.iliev.domani.model.dto;


import com.iliev.domani.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

public class EditUserDto {

    private Long userId;
    @Size(min = 5,max = 30)
    @NotBlank
    String fullName;
    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Enter valid email!")
    @UniqueEmail
    String email;
    @NumberFormat
    int roles;

    public String getFullName() {
        return fullName;
    }

    public EditUserDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public EditUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getRoles() {
        return roles;
    }

    public EditUserDto setRoles(int roles) {
        this.roles = roles;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public EditUserDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
