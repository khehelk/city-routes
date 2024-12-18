package ru.khehelk.cityroutes.reviewservice.web.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khehelk.cityroutes.reviewservice.service.dto.ReviewCreationDto;
import ru.khehelk.cityroutes.reviewservice.service.dto.ReviewDto;

@Tag(name = "API отзывов")
@RequestMapping("/api/v1/reviews")
public interface ReviewApi {

    @Operation(summary = "Получить комментарии маршрута")
    @GetMapping("{id}")
    ResponseEntity<List<ReviewDto>> findComments(@PathVariable("id") Long id,
                                                 @RequestParam Long limit);

    @Operation(summary = "Добавить комментарий")
    @PostMapping
    ResponseEntity<String> addReview(@RequestBody ReviewCreationDto newReview);

}
