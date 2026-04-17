package com.realestate.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "property_reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private Integer rating; // 1-5 stars
    private String reviewText;
    private String reviewTitle;
    
    private LocalDateTime reviewDate;
    private Integer helpfulCount = 0;
    private Boolean isVerifiedPurchase = false;
    
    @PrePersist
    protected void onCreate() {
        reviewDate = LocalDateTime.now();
    }
}
