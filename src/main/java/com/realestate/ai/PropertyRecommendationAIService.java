package com.realestate.ai;

import com.realestate.model.AIRecommendationRequest;
import com.realestate.model.Property;
import com.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyRecommendationAIService {

    @Autowired
    private PropertyRepository propertyRepository;

    /**
     * Get AI-powered property recommendations based on user preferences
     */
    public List<Property> getRecommendations(AIRecommendationRequest request) {
        List<Property> allProperties = propertyRepository.findAll();
        
        return allProperties.stream()
                .filter(p -> request.getMaxPrice() == null || p.getPrice().doubleValue() <= request.getMaxPrice())
                .filter(p -> request.getMinPrice() == null || p.getPrice().doubleValue() >= request.getMinPrice())
                .filter(p -> request.getCity() == null || p.getCity().equalsIgnoreCase(request.getCity()))
                .filter(p -> request.getState() == null || p.getState().equalsIgnoreCase(request.getState()))
                .filter(p -> request.getMinBedrooms() == null || p.getBedrooms() >= request.getMinBedrooms())
                .filter(p -> request.getMaxBedrooms() == null || p.getBedrooms() <= request.getMaxBedrooms())
                .filter(p -> request.getPropertyType() == null || p.getPropertyType().equalsIgnoreCase(request.getPropertyType()))
                .filter(Property::getIsAvailable)
                .limit(request.getResultsLimit())
                .collect(Collectors.toList());
    }

    /**
     * Predict property price using AI model
     * This would integrate with DeepLearning4j or similar ML library
     */
    public Double predictPropertyPrice(Property property) {
        // Placeholder for actual ML model prediction
        // In production, this would use a trained neural network
        
        double basePrice = 100000;
        double pricePerSqFt = 150;
        double bedroomBonus = property.getBedrooms() * 50000;
        double bathroomBonus = property.getBathrooms() * 30000;
        
        double estimatedPrice = basePrice 
                + (property.getSquareFeet() != null ? property.getSquareFeet() * pricePerSqFt : 0)
                + bedroomBonus 
                + bathroomBonus;
        
        return estimatedPrice;
    }

    /**
     * Calculate property similarity score for recommendations
     */
    public Double calculateSimilarityScore(Property property1, Property property2) {
        double similarity = 0;
        int matchingFactors = 0;
        
        // Price similarity (within 20% range)
        if (Math.abs(property1.getPrice().doubleValue() - property2.getPrice().doubleValue()) 
                / property1.getPrice().doubleValue() < 0.2) {
            similarity += 20;
        }
        matchingFactors++;
        
        // Location similarity
        if (property1.getCity().equalsIgnoreCase(property2.getCity())) {
            similarity += 25;
        }
        matchingFactors++;
        
        // Property type similarity
        if (property1.getPropertyType().equalsIgnoreCase(property2.getPropertyType())) {
            similarity += 20;
        }
        matchingFactors++;
        
        // Bedroom similarity
        if (Math.abs(property1.getBedrooms() - property2.getBedrooms()) <= 1) {
            similarity += 15;
        }
        matchingFactors++;
        
        // Amenities similarity
        if (property1.getAmenities() != null && property2.getAmenities() != null) {
            long commonAmenities = property1.getAmenities().stream()
                    .filter(property2.getAmenities()::contains)
                    .count();
            similarity += (commonAmenities * 5);
        }
        matchingFactors++;
        
        return matchingFactors > 0 ? similarity / 5 : 0;
    }

    /**
     * Get trending properties based on views and ratings
     */
    public List<Property> getTrendingProperties(int limit) {
        return propertyRepository.findAll().stream()
                .filter(Property::getIsAvailable)
                .sorted((p1, p2) -> {
                    Double rating1 = p1.getAverageRating() != null ? p1.getAverageRating() : 0;
                    Double rating2 = p2.getAverageRating() != null ? p2.getAverageRating() : 0;
                    return rating2.compareTo(rating1);
                })
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Rank properties based on multiple criteria
     */
    public List<Property> rankProperties(List<Property> properties, AIRecommendationRequest criteria) {
        return properties.stream()
                .sorted((p1, p2) -> {
                    double score1 = calculatePropertyScore(p1, criteria);
                    double score2 = calculatePropertyScore(p2, criteria);
                    return Double.compare(score2, score1);
                })
                .collect(Collectors.toList());
    }

    private double calculatePropertyScore(Property property, AIRecommendationRequest criteria) {
        double score = 0;
        
        // Price score (lower is better if within budget)
        if (criteria.getMaxPrice() != null) {
            double priceDiff = criteria.getMaxPrice() - property.getPrice().doubleValue();
            score += Math.max(0, priceDiff / criteria.getMaxPrice() * 30);
        }
        
        // Bedroom score
        if (criteria.getMinBedrooms() != null && property.getBedrooms() >= criteria.getMinBedrooms()) {
            score += 20;
        }
        
        // Rating score
        if (property.getAverageRating() != null) {
            score += property.getAverageRating() * 5;
        }
        
        // Available status score
        if (property.getIsAvailable()) {
            score += 15;
        }
        
        return score;
    }
}
