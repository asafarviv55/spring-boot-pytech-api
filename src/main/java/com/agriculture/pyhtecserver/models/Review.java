package com.agriculture.pyhtecserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "title")
    private String title;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReviewStatus status;

    @Column(name = "verified_purchase")
    private boolean verifiedPurchase;

    @Column(name = "helpful_count")
    private int helpfulCount;

    @PrePersist
    protected void onCreate() {
        reviewDate = LocalDateTime.now();
        if (status == null) {
            status = ReviewStatus.PENDING;
        }
        helpfulCount = 0;
    }

    public enum ReviewStatus {
        PENDING,
        APPROVED,
        REJECTED,
        FLAGGED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", productId=" + (product != null ? product.getId() : null) +
                ", customerId=" + (customer != null ? customer.getId() : null) +
                ", rating=" + rating +
                ", status=" + status +
                ", verifiedPurchase=" + verifiedPurchase +
                '}';
    }
}
