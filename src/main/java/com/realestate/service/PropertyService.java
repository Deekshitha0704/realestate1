package com.realestate.service;

import com.realestate.model.Property;
import com.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    /**
     * Get all properties with optional filters
     */
    @Cacheable("properties")
    public List<Property> getAllProperties(String city, String propertyType) {
        List<Property> properties = propertyRepository.findAll();
        
        return properties.stream()
                .filter(p -> city == null || p.getCity().equalsIgnoreCase(city))
                .filter(p -> propertyType == null || p.getPropertyType().equalsIgnoreCase(propertyType))
                .collect(Collectors.toList());
    }

    /**
     * Get property by ID
     */
    @Cacheable(value = "property", key = "#id")
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    /**
     * Save new property
     */
    @CacheEvict(value = "properties", allEntries = true)
    public Property saveProperty(Property property) {
        property.setIsAvailable(true);
        return propertyRepository.save(property);
    }

    /**
     * Update existing property
     */
    @CacheEvict(value = {"properties", "property"}, allEntries = true)
    public Property updateProperty(Long id, Property propertyDetails) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        
        if (propertyDetails.getTitle() != null) property.setTitle(propertyDetails.getTitle());
        if (propertyDetails.getDescription() != null) property.setDescription(propertyDetails.getDescription());
        if (propertyDetails.getPrice() != null) property.setPrice(propertyDetails.getPrice());
        if (propertyDetails.getBedrooms() != null) property.setBedrooms(propertyDetails.getBedrooms());
        if (propertyDetails.getBathrooms() != null) property.setBathrooms(propertyDetails.getBathrooms());
        if (propertyDetails.getSquareFeet() != null) property.setSquareFeet(propertyDetails.getSquareFeet());
        if (propertyDetails.getIsAvailable() != null) property.setIsAvailable(propertyDetails.getIsAvailable());
        
        return propertyRepository.save(property);
    }

    /**
     * Delete property
     */
    @CacheEvict(value = {"properties", "property"}, allEntries = true)
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    /**
     * Search properties with multiple criteria
     */
    public List<Property> searchProperties(String city, Double minPrice, Double maxPrice, Integer minBedrooms) {
        List<Property> properties = propertyRepository.findAll();
        
        return properties.stream()
                .filter(p -> city == null || p.getCity().equalsIgnoreCase(city))
                .filter(p -> minPrice == null || p.getPrice().doubleValue() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice().doubleValue() <= maxPrice)
                .filter(p -> minBedrooms == null || p.getBedrooms() >= minBedrooms)
                .filter(p -> p.getIsAvailable())
                .collect(Collectors.toList());
    }

    /**
     * Get average price by city
     */
    @Cacheable(value = "averagePrice", key = "#city")
    public Double getAveragePriceByCity(String city) {
        List<Property> properties = propertyRepository.findAll();
        
        return properties.stream()
                .filter(p -> city == null || p.getCity().equalsIgnoreCase(city))
                .mapToDouble(p -> p.getPrice().doubleValue())
                .average()
                .orElse(0.0);
    }

    /**
     * Get available properties count
     */
    public Long getAvailablePropertiesCount() {
        return propertyRepository.findAll().stream()
                .filter(Property::getIsAvailable)
                .count();
    }
}
