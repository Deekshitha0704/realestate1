package com.realestate.ai;

import com.realestate.model.AIRecommendationRequest;
import com.realestate.model.Property;
import com.realestate.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PropertyRecommendationAIServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyRecommendationAIService aiService;

    private List<Property> sampleProperties;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProperties = createSampleProperties();
    }

    @Test
    public void testGetRecommendations() {
        when(propertyRepository.findAll()).thenReturn(sampleProperties);
        
        AIRecommendationRequest request = new AIRecommendationRequest();
        request.setMaxPrice(1000000.0);
        request.setCity("New York");
        request.setMinBedrooms(2);
        
        List<Property> recommendations = aiService.getRecommendations(request);
        
        assertNotNull(recommendations);
        assertTrue(recommendations.stream().allMatch(p -> p.getPrice().doubleValue() <= 1000000));
        assertTrue(recommendations.stream().allMatch(p -> p.getCity().equals("New York")));
    }

    @Test
    public void testPredictPropertyPrice() {
        Property property = new Property();
        property.setSquareFeet(2500.0);
        property.setBedrooms(3);
        property.setBathrooms(2);
        
        Double predictedPrice = aiService.predictPropertyPrice(property);
        
        assertNotNull(predictedPrice);
        assertTrue(predictedPrice > 0);
    }

    @Test
    public void testCalculateSimilarityScore() {
        Property prop1 = sampleProperties.get(0);
        Property prop2 = sampleProperties.get(1);
        
        Double similarity = aiService.calculateSimilarityScore(prop1, prop2);
        
        assertNotNull(similarity);
        assertTrue(similarity >= 0 && similarity <= 100);
    }

    @Test
    public void testGetTrendingProperties() {
        when(propertyRepository.findAll()).thenReturn(sampleProperties);
        
        List<Property> trending = aiService.getTrendingProperties(2);
        
        assertNotNull(trending);
        assertTrue(trending.size() <= 2);
    }

    @Test
    public void testRankProperties() {
        AIRecommendationRequest criteria = new AIRecommendationRequest();
        criteria.setMaxPrice(1200000.0);
        criteria.setMinBedrooms(2);
        
        List<Property> ranked = aiService.rankProperties(sampleProperties, criteria);
        
        assertNotNull(ranked);
        assertFalse(ranked.isEmpty());
    }

    private List<Property> createSampleProperties() {
        List<Property> properties = new ArrayList<>();

        Property prop1 = new Property();
        prop1.setId(1L);
        prop1.setTitle("Downtown Apartment");
        prop1.setCity("New York");
        prop1.setPrice(new BigDecimal("750000"));
        prop1.setBedrooms(3);
        prop1.setBathrooms(2);
        prop1.setSquareFeet(2500.0);
        prop1.setPropertyType("APARTMENT");
        prop1.setIsAvailable(true);
        prop1.setAverageRating(4.8);
        prop1.setAmenities(Arrays.asList("gym", "parking"));
        properties.add(prop1);

        Property prop2 = new Property();
        prop2.setId(2L);
        prop2.setTitle("Suburban Home");
        prop2.setCity("San Francisco");
        prop2.setPrice(new BigDecimal("1500000"));
        prop2.setBedrooms(5);
        prop2.setBathrooms(3);
        prop2.setSquareFeet(4200.0);
        prop2.setPropertyType("HOUSE");
        prop2.setIsAvailable(true);
        prop2.setAverageRating(4.6);
        prop2.setAmenities(Arrays.asList("pool", "garage"));
        properties.add(prop2);

        return properties;
    }
}
