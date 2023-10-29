package com.iliev.domani.model.view;

import java.util.List;

public class UserView {

    private Long userId;

    private String fullName;

    private String email;

    private List<RoleView> userRoles;

    public UserView() {
    }

    public Long getUserId() {
        return userId;
    }

    public UserView setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserView setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserView setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<RoleView> getUserRoles() {
        return userRoles;
    }

    public UserView setUserRoles(List<RoleView> userRoles) {
        this.userRoles = userRoles;
        return this;
    }
}
