package ru.khehelk.cityroutes.reviewservice.service.dto;

import java.time.ZonedDateTime;

public record ReviewDto(
    String author,
    String comment,
    ZonedDateTime date) {
}
