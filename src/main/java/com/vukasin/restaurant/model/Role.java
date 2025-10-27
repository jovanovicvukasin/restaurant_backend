package com.vukasin.restaurant.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public enum Role implements GrantedAuthority {

    ADMIN,
    CUSTOMER;

    @Override
    public String getAuthority() {
        return name();
    }
}
