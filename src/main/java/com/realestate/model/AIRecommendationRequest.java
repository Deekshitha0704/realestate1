package com.realestate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIRecommendationRequest {
    private Double maxPrice;
    private Double minPrice;
    private String city;
    private String state;
    private Integer minBedrooms;
    private Integer maxBedrooms;
    private String propertyType;
    private List<String> preferredAmenities;
    private Integer resultsLimit = 10;
}
