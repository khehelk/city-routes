package ru.khehelk.cityroutes.authservice.service.dto;

public record AuthRequest(
    String email,
    String password
) {
}
