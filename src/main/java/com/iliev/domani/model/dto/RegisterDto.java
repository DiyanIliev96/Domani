package com.iliev.domani.model.dto;

import com.iliev.domani.validation.FieldMatcher;
import com.iliev.domani.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@FieldMatcher(first = "password",second = "confirmPassword")
public class RegisterDto {
    @Size(min = 5,max = 30)
    @NotBlank
    private String fullName;

    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Enter valid email!")
    @UniqueEmail
    private String email;

    @Size(min = 5,max = 30,message = "Password must be between 5 and 30 characters!")
    private String password;
    @Size(min = 5,max = 30,message = "Confirm password must be between 5 and 30 characters!")
    private String confirmPassword;

    public RegisterDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public RegisterDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public RegisterDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
