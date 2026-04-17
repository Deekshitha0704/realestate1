package com.realestate.controller;

import com.realestate.model.Property;
import com.realestate.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    /**
     * Get all properties
     */
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String propertyType) {
        List<Property> properties = propertyService.getAllProperties(city, propertyType);
        return ResponseEntity.ok(properties);
    }

    /**
     * Get property by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable Long id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        if (property.isPresent()) {
            return ResponseEntity.ok(property.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Property not found with ID: " + id);
    }

    /**
     * Create new property
     */
    @PostMapping
    public ResponseEntity<?> createProperty(@RequestBody Property property) {
        try {
            Property savedProperty = propertyService.saveProperty(property);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProperty);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating property: " + e.getMessage());
        }
    }

    /**
     * Update property
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, @RequestBody Property property) {
        try {
            Property updatedProperty = propertyService.updateProperty(id, property);
            return ResponseEntity.ok(updatedProperty);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Property not found with ID: " + id);
        }
    }

    /**
     * Delete property
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        try {
            propertyService.deleteProperty(id);
            return ResponseEntity.ok("Property deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Property not found with ID: " + id);
        }
    }

    /**
     * Search properties by location and price range
     */
    @GetMapping("/search")
    public ResponseEntity<List<Property>> searchProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minBedrooms) {
        List<Property> properties = propertyService.searchProperties(city, minPrice, maxPrice, minBedrooms);
        return ResponseEntity.ok(properties);
    }

    /**
     * Get property statistics
     */
    @GetMapping("/stats/average-price")
    public ResponseEntity<?> getAveragePrice(
            @RequestParam(required = false) String city) {
        Double avgPrice = propertyService.getAveragePriceByCity(city);
        return ResponseEntity.ok("Average Price: $" + avgPrice);
    }
}
