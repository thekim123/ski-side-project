package com.project.ski.domain.user;

public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_ANONYMOUS("ROLE_ANONYMOUS");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
