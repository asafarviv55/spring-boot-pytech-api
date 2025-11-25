package com.agriculture.pyhtecserver.controllers;

import com.agriculture.pyhtecserver.models.Review;
import com.agriculture.pyhtecserver.services.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        log.info("Getting all reviews");
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        log.info("Getting review by id: {}", id);
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        log.info("Creating new review for product: {}", review.getProduct().getId());
        Review created = reviewService.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        log.info("Updating review with id: {}", id);
        try {
            Review updated = reviewService.updateReview(id, review);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        log.info("Deleting review with id: {}", id);
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable Long productId) {
        log.info("Getting reviews for product: {}", productId);
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }

    @GetMapping("/product/{productId}/approved")
    public ResponseEntity<List<Review>> getApprovedReviewsByProduct(@PathVariable Long productId) {
        log.info("Getting approved reviews for product: {}", productId);
        return ResponseEntity.ok(reviewService.getApprovedReviewsByProduct(productId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Review>> getReviewsByCustomer(@PathVariable Long customerId) {
        log.info("Getting reviews for customer: {}", customerId);
        return ResponseEntity.ok(reviewService.getReviewsByCustomer(customerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Review>> getReviewsByStatus(@PathVariable Review.ReviewStatus status) {
        log.info("Getting reviews by status: {}", status);
        return ResponseEntity.ok(reviewService.getReviewsByStatus(status));
    }

    @GetMapping("/rating")
    public ResponseEntity<List<Review>> getReviewsByMinimumRating(@RequestParam int minRating) {
        log.info("Getting reviews with minimum rating: {}", minRating);
        return ResponseEntity.ok(reviewService.getReviewsByMinimumRating(minRating));
    }

    @GetMapping("/verified")
    public ResponseEntity<List<Review>> getVerifiedPurchaseReviews() {
        log.info("Getting verified purchase reviews");
        return ResponseEntity.ok(reviewService.getVerifiedPurchaseReviews());
    }

    @GetMapping("/product/{productId}/average-rating")
    public ResponseEntity<Double> getAverageRatingForProduct(@PathVariable Long productId) {
        log.info("Getting average rating for product: {}", productId);
        return ResponseEntity.ok(reviewService.getAverageRatingForProduct(productId));
    }

    @GetMapping("/product/{productId}/count")
    public ResponseEntity<Long> getReviewCountForProduct(@PathVariable Long productId) {
        log.info("Getting review count for product: {}", productId);
        return ResponseEntity.ok(reviewService.getReviewCountForProduct(productId));
    }

    @GetMapping("/most-helpful")
    public ResponseEntity<List<Review>> getMostHelpfulReviews() {
        log.info("Getting most helpful reviews");
        return ResponseEntity.ok(reviewService.getMostHelpfulReviews());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Review> updateReviewStatus(@PathVariable Long id, @RequestParam Review.ReviewStatus status) {
        log.info("Updating review {} status to {}", id, status);
        try {
            Review updated = reviewService.updateReviewStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/helpful")
    public ResponseEntity<Review> incrementHelpfulCount(@PathVariable Long id) {
        log.info("Incrementing helpful count for review: {}", id);
        try {
            Review updated = reviewService.incrementHelpfulCount(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
