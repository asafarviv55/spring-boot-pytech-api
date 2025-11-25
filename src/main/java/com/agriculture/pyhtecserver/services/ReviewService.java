package com.agriculture.pyhtecserver.services;

import com.agriculture.pyhtecserver.models.Review;
import com.agriculture.pyhtecserver.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review reviewDetails) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        review.setProduct(reviewDetails.getProduct());
        review.setCustomer(reviewDetails.getCustomer());
        review.setRating(reviewDetails.getRating());
        review.setTitle(reviewDetails.getTitle());
        review.setComment(reviewDetails.getComment());
        review.setStatus(reviewDetails.getStatus());
        review.setVerifiedPurchase(reviewDetails.isVerifiedPurchase());
        review.setHelpfulCount(reviewDetails.getHelpfulCount());

        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Review> getReviewsByCustomer(Long customerId) {
        return reviewRepository.findByCustomerId(customerId);
    }

    public List<Review> getReviewsByStatus(Review.ReviewStatus status) {
        return reviewRepository.findByStatus(status);
    }

    public List<Review> getApprovedReviewsByProduct(Long productId) {
        return reviewRepository.findApprovedReviewsByProduct(productId);
    }

    public List<Review> getReviewsByMinimumRating(int minRating) {
        return reviewRepository.findByMinimumRating(minRating);
    }

    public List<Review> getVerifiedPurchaseReviews() {
        return reviewRepository.findVerifiedPurchaseReviews();
    }

    public Double getAverageRatingForProduct(Long productId) {
        return reviewRepository.getAverageRatingForProduct(productId);
    }

    public Long getReviewCountForProduct(Long productId) {
        return reviewRepository.getReviewCountForProduct(productId);
    }

    public Review updateReviewStatus(Long id, Review.ReviewStatus status) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        review.setStatus(status);
        return reviewRepository.save(review);
    }

    public Review incrementHelpfulCount(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        review.setHelpfulCount(review.getHelpfulCount() + 1);
        return reviewRepository.save(review);
    }

    public List<Review> getMostHelpfulReviews() {
        return reviewRepository.findMostHelpfulReviews();
    }
}
