package com.realestate.repository;

import com.realestate.model.PropertyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyReviewRepository extends JpaRepository<PropertyReview, Long> {
    
    /**
     * Find reviews by property ID
     */
    List<PropertyReview> findByPropertyId(Long propertyId);
    
    /**
     * Find reviews by user ID
     */
    List<PropertyReview> findByUserId(Long userId);
    
    /**
     * Find reviews by property ID sorted by rating
     */
    @Query("SELECT r FROM PropertyReview r WHERE r.property.id = ?1 ORDER BY r.rating DESC")
    List<PropertyReview> findByPropertyIdOrderByRating(Long propertyId);
    
    /**
     * Find verified purchase reviews
     */
    List<PropertyReview> findByPropertyIdAndIsVerifiedPurchase(Long propertyId, Boolean isVerified);
    
    /**
     * Get average rating for property
     */
    @Query("SELECT AVG(r.rating) FROM PropertyReview r WHERE r.property.id = ?1")
    Double getAverageRatingForProperty(Long propertyId);
}
