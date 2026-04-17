package com.realestate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertySearchDTO {
    private String city;
    private String state;
    private Double minPrice;
    private Double maxPrice;
    private Integer minBedrooms;
    private Integer maxBedrooms;
    private String propertyType;
    private Boolean isAvailable;
    private Integer pageNumber = 0;
    private Integer pageSize = 10;
    private String sortBy = "price";
}
