package ru.khehelk.cityroutes.reviewservice.web.rest;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.khehelk.cityroutes.reviewservice.service.ReviewService;
import ru.khehelk.cityroutes.reviewservice.service.dto.ReviewCreationDto;
import ru.khehelk.cityroutes.reviewservice.service.dto.ReviewDto;
import ru.khehelk.cityroutes.reviewservice.web.api.ReviewApi;

@RestController
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {

    private final ReviewService reviewService;


    @Override
    public ResponseEntity<List<ReviewDto>> findComments(Long id, Long limit) {
        return ResponseEntity.ok(reviewService.findComments(id, limit));
    }

    @Override
    public ResponseEntity<String> addReview(ReviewCreationDto newReview) {
        reviewService.addReview(newReview);
        return ResponseEntity.ok("Отзыв успешно создан");
    }
}
