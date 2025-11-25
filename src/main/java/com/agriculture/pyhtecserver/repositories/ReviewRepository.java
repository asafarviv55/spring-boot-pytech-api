package com.agriculture.pyhtecserver.repositories;

import com.agriculture.pyhtecserver.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);

    List<Review> findByCustomerId(Long customerId);

    List<Review> findByStatus(Review.ReviewStatus status);

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.status = 'APPROVED'")
    List<Review> findApprovedReviewsByProduct(@Param("productId") Long productId);

    @Query("SELECT r FROM Review r WHERE r.rating >= :minRating")
    List<Review> findByMinimumRating(@Param("minRating") int minRating);

    @Query("SELECT r FROM Review r WHERE r.verifiedPurchase = true")
    List<Review> findVerifiedPurchaseReviews();

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId AND r.status = 'APPROVED'")
    Double getAverageRatingForProduct(@Param("productId") Long productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId AND r.status = 'APPROVED'")
    Long getReviewCountForProduct(@Param("productId") Long productId);

    @Query("SELECT r FROM Review r ORDER BY r.reviewDate DESC")
    List<Review> findAllOrderedByDate();

    @Query("SELECT r FROM Review r ORDER BY r.helpfulCount DESC")
    List<Review> findMostHelpfulReviews();
}
