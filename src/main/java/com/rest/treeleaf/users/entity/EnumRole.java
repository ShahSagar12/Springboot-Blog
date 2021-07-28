package com.rest.treeleaf.users.entity;

public enum EnumRole {
    ADMIN("ADMIN"), USER("USER");

    private final String role;

    private EnumRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
