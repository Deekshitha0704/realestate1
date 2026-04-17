package com.realestate.service;

import com.realestate.model.PropertyReview;
import com.realestate.repository.PropertyReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PropertyReviewService {

    @Autowired
    private PropertyReviewRepository reviewRepository;

    /**
     * Get reviews by property
     */
    @Cacheable(value = "propertyReviews", key = "#propertyId")
    public List<PropertyReview> getReviewsByProperty(Long propertyId) {
        return reviewRepository.findByPropertyIdOrderByRating(propertyId);
    }

    /**
     * Get verified reviews only
     */
    public List<PropertyReview> getVerifiedReviews(Long propertyId) {
        return reviewRepository.findByPropertyIdAndIsVerifiedPurchase(propertyId, true);
    }

    /**
     * Get reviews by user
     */
    public List<PropertyReview> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    /**
     * Save new review
     */
    @CacheEvict(value = "propertyReviews", allEntries = true)
    public PropertyReview saveReview(PropertyReview review) {
        return reviewRepository.save(review);
    }

    /**
     * Update review
     */
    @CacheEvict(value = "propertyReviews", allEntries = true)
    public PropertyReview updateReview(Long reviewId, PropertyReview reviewDetails) {
        PropertyReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (reviewDetails.getRating() != null) review.setRating(reviewDetails.getRating());
        if (reviewDetails.getReviewText() != null) review.setReviewText(reviewDetails.getReviewText());
        if (reviewDetails.getReviewTitle() != null) review.setReviewTitle(reviewDetails.getReviewTitle());

        return reviewRepository.save(review);
    }

    /**
     * Delete review
     */
    @CacheEvict(value = "propertyReviews", allEntries = true)
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    /**
     * Get average rating
     */
    @Cacheable(value = "averageRating", key = "#propertyId")
    public Double getAverageRating(Long propertyId) {
        return reviewRepository.getAverageRatingForProperty(propertyId);
    }

    /**
     * Get review count for property
     */
    public Long getReviewCount(Long propertyId) {
        return (long) reviewRepository.findByPropertyId(propertyId).size();
    }
}
