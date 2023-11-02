package com.iliev.domani.model.dto;


import com.iliev.domani.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EditUserDto {
    @Size(min = 5,max = 30)
    @NotBlank
    String fullName;
    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Enter valid email!")
    @UniqueEmail
    String email;

    String role;

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

    public String getRole() {
        return role;
    }

    public EditUserDto setRole(String role) {
        this.role = role;
        return this;
    }
}
