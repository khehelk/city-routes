package ru.khehelk.cityroutes.reviewservice.service.dto;

public record ReviewCreationDto(
    String comment,
    Long routeId
) {
}
