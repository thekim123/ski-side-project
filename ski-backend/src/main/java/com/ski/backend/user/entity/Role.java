package com.ski.backend.user.entity;

// TODO: 클래스로 바꿀겁니다.
public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_GUEST("ROLE_GUEST"),
    ROLE_ANONYMOUS("ROLE_ANONYMOUS");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
