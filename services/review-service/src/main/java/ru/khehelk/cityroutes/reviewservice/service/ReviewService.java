package ru.khehelk.cityroutes.reviewservice.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.businesslogic.repository.RouteRepository;
import ru.khehelk.cityroutes.domain.model.Route;
import ru.khehelk.cityroutes.reviewservice.domain.Review;
import ru.khehelk.cityroutes.reviewservice.domain.ReviewId;
import ru.khehelk.cityroutes.reviewservice.domain.User;
import ru.khehelk.cityroutes.reviewservice.repository.ReviewRepository;
import ru.khehelk.cityroutes.reviewservice.repository.UserRepository;
import ru.khehelk.cityroutes.reviewservice.service.dto.ReviewCreationDto;
import ru.khehelk.cityroutes.reviewservice.service.dto.ReviewDto;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final RouteRepository routeRepository;

    @Transactional(readOnly = true)
    public List<ReviewDto> findComments(Long id, Long limit) {
        Pageable pageable = PageRequest.of(0, Math.toIntExact(limit));
        return reviewRepository.findById_RouteIdOrderById_DateDesc(id, pageable)
            .stream().map(review ->
                new ReviewDto(
                    review.getUser().getUsername(),
                    review.getComment(),
                    review.getId().getDate()
                ))
            .collect(Collectors.toList());
    }

    public void addReview(ReviewCreationDto newReview) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        Route route = routeRepository.findById(newReview.routeId())
            .orElseThrow(EntityNotFoundException::new);
        Review review = new Review();
        review.setComment(newReview.comment());
        review.setId(new ReviewId(user.getId(), route.getId(), ZonedDateTime.now()));
        review.setUser(user);
        review.setRoute(route);
        reviewRepository.save(review);
    }
}
