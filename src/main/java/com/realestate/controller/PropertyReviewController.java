package com.realestate.controller;

import com.realestate.model.PropertyReview;
import com.realestate.service.PropertyReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PropertyReviewController {

    @Autowired
    private PropertyReviewService reviewService;

    /**
     * Get all reviews for a property
     */
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<PropertyReview>> getPropertyReviews(
            @PathVariable Long propertyId,
            @RequestParam(required = false, defaultValue = "false") Boolean verifiedOnly) {
        List<PropertyReview> reviews = verifiedOnly 
            ? reviewService.getVerifiedReviews(propertyId)
            : reviewService.getReviewsByProperty(propertyId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get user's reviews
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PropertyReview>> getUserReviews(@PathVariable Long userId) {
        List<PropertyReview> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Create a new review
     */
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody PropertyReview review) {
        try {
            PropertyReview savedReview = reviewService.saveReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating review: " + e.getMessage());
        }
    }

    /**
     * Update a review
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(
            @PathVariable Long reviewId,
            @RequestBody PropertyReview review) {
        try {
            PropertyReview updatedReview = reviewService.updateReview(reviewId, review);
            return ResponseEntity.ok(updatedReview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Review not found");
        }
    }

    /**
     * Delete a review
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok("Review deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Review not found");
        }
    }

    /**
     * Get average rating for property
     */
    @GetMapping("/property/{propertyId}/average-rating")
    public ResponseEntity<?> getAverageRating(@PathVariable Long propertyId) {
        Double avgRating = reviewService.getAverageRating(propertyId);
        return ResponseEntity.ok("Average Rating: " + (avgRating != null ? avgRating : 0));
    }
}
