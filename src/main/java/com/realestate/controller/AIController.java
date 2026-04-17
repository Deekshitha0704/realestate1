package com.realestate.controller;

import com.realestate.ai.PropertyRecommendationAIService;
import com.realestate.model.AIRecommendationRequest;
import com.realestate.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AIController {

    @Autowired
    private PropertyRecommendationAIService aiService;

    /**
     * Get AI-powered property recommendations
     */
    @PostMapping("/recommendations")
    public ResponseEntity<List<Property>> getRecommendations(@RequestBody AIRecommendationRequest request) {
        List<Property> recommendations = aiService.getRecommendations(request);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Predict property price using AI
     */
    @PostMapping("/predict-price")
    public ResponseEntity<?> predictPrice(@RequestBody Property property) {
        Double predictedPrice = aiService.predictPropertyPrice(property);
        Map<String, Object> response = new HashMap<>();
        response.put("propertyId", property.getId());
        response.put("actualPrice", property.getPrice());
        response.put("predictedPrice", predictedPrice);
        response.put("variance", Math.abs(predictedPrice - property.getPrice().doubleValue()));
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get trending properties
     */
    @GetMapping("/trending")
    public ResponseEntity<List<Property>> getTrendingProperties(
            @RequestParam(defaultValue = "10") int limit) {
        List<Property> trending = aiService.getTrendingProperties(limit);
        return ResponseEntity.ok(trending);
    }

    /**
     * Get similar properties
     */
    @GetMapping("/similar/{propertyId}")
    public ResponseEntity<?> getSimilarProperties(
            @PathVariable Long propertyId,
            @RequestParam(defaultValue = "5") int limit) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Similar properties endpoint");
        response.put("limit", limit);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "AI Service is running");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
}
