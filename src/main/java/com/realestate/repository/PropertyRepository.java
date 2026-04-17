package com.realestate.repository;

import com.realestate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    
    /**
     * Find properties by city
     */
    List<Property> findByCity(String city);
    
    /**
     * Find properties by property type
     */
    List<Property> findByPropertyType(String propertyType);
    
    /**
     * Find properties by city and property type
     */
    List<Property> findByCityAndPropertyType(String city, String propertyType);
    
    /**
     * Find available properties
     */
    List<Property> findByIsAvailable(Boolean isAvailable);
    
    /**
     * Custom query to find properties within price range
     */
    @Query("SELECT p FROM Property p WHERE p.price BETWEEN ?1 AND ?2 AND p.isAvailable = true")
    List<Property> findPropertiesInPriceRange(Double minPrice, Double maxPrice);
    
    /**
     * Find properties by city with minimum bedrooms
     */
    @Query("SELECT p FROM Property p WHERE p.city = ?1 AND p.bedrooms >= ?2 AND p.isAvailable = true")
    List<Property> findByCityAndMinBedrooms(String city, Integer minBedrooms);
}
