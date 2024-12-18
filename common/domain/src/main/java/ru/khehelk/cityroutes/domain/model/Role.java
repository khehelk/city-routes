package ru.khehelk.cityroutes.domain.model;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    final String roleValue;

    Role(String roleAdmin) {
        this.roleValue = roleAdmin;
    }
}
