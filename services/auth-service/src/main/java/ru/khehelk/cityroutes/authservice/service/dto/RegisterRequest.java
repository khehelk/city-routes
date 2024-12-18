package ru.khehelk.cityroutes.authservice.service.dto;

public record RegisterRequest(
    String email,
    String password
) {
}
