package com.iliev.domani.model.dto;



public class EditUserDto {

    String fullName;

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
