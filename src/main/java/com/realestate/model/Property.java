package com.realestate.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    
    private BigDecimal price;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double squareFeet;
    private Integer yearBuilt;
    
    private String propertyType; // HOUSE, APARTMENT, CONDO, LAND
    private String listingType; // SALE, RENT
    
    @ElementCollection
    private List<String> amenities; // swimming pool, gym, parking, etc.
    
    private Double latitude;
    private Double longitude;
    
    private LocalDateTime listedDate;
    private Boolean isAvailable = true;
    private Double averageRating;
    
    private String imageUrl; // URL to property image
    
    @Column(name = "feature_vector", length = 1000)
    private String featureVector; // For AI recommendations
}
