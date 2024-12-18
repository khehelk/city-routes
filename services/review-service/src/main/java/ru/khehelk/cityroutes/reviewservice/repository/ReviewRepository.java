package ru.khehelk.cityroutes.reviewservice.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.reviewservice.domain.Review;
import ru.khehelk.cityroutes.reviewservice.domain.ReviewId;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    List<Review> findById_RouteIdOrderById_DateDesc(Long routeId, Pageable pageable);
}