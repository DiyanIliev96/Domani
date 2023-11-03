package com.iliev.domani.model.dto;

import com.iliev.domani.model.enums.RoleNameEnum;
import com.iliev.domani.validation.FieldMatcher;
import com.iliev.domani.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@FieldMatcher(first = "password",second = "confirmPassword")

public class CreateUserDto {
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
    @NotNull
    private RoleNameEnum role;

    public CreateUserDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public CreateUserDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CreateUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CreateUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public CreateUserDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public RoleNameEnum getRole() {
        return role;
    }

    public CreateUserDto setRole(RoleNameEnum role) {
        this.role = role;
        return this;
    }
}
