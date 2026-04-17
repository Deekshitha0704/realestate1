package com.realestate.config;

import com.realestate.model.Property;
import com.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize sample data only if database is empty
        if (propertyRepository.count() == 0) {
            initializeSampleProperties();
        }
    }

    private void initializeSampleProperties() {
        Property prop1 = new Property();
        prop1.setTitle("Modern Downtown Apartment");
        prop1.setDescription("Beautiful 3-bedroom apartment in the heart of downtown with stunning city views");
        prop1.setAddress("123 Main Street");
        prop1.setCity("New York");
        prop1.setState("NY");
        prop1.setZipCode("10001");
        prop1.setPrice(new BigDecimal("750000"));
        prop1.setBedrooms(3);
        prop1.setBathrooms(2);
        prop1.setSquareFeet(2500.0);
        prop1.setYearBuilt(2020);
        prop1.setPropertyType("APARTMENT");
        prop1.setListingType("SALE");
        prop1.setAmenities(Arrays.asList("gym", "parking", "concierge", "rooftop"));
        prop1.setLatitude(40.7128);
        prop1.setLongitude(-74.0060);
        prop1.setListedDate(LocalDateTime.now());
        prop1.setAverageRating(4.8);
        prop1.setImageUrl("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=600&h=400");
        propertyRepository.save(prop1);

        Property prop2 = new Property();
        prop2.setTitle("Suburban Family Home");
        prop2.setDescription("Spacious 5-bedroom house with large backyard and swimming pool");
        prop2.setAddress("456 Oak Avenue");
        prop2.setCity("San Francisco");
        prop2.setState("CA");
        prop2.setZipCode("94102");
        prop2.setPrice(new BigDecimal("1500000"));
        prop2.setBedrooms(5);
        prop2.setBathrooms(3);
        prop2.setSquareFeet(4200.0);
        prop2.setYearBuilt(2015);
        prop2.setPropertyType("HOUSE");
        prop2.setListingType("SALE");
        prop2.setAmenities(Arrays.asList("pool", "garage", "patio", "garden"));
        prop2.setLatitude(37.7749);
        prop2.setLongitude(-122.4194);
        prop2.setListedDate(LocalDateTime.now().minusDays(7));
        prop2.setAverageRating(4.6);
        prop2.setImageUrl("https://images.unsplash.com/photo-1560518883-ce09059eeffa?w=600&h=400");
        propertyRepository.save(prop2);

        Property prop3 = new Property();
        prop3.setTitle("Luxury Condo with Ocean View");
        prop3.setDescription("Premium condo with panoramic ocean views and high-end finishes");
        prop3.setAddress("789 Beach Drive");
        prop3.setCity("Miami");
        prop3.setState("FL");
        prop3.setZipCode("33139");
        prop3.setPrice(new BigDecimal("1200000"));
        prop3.setBedrooms(4);
        prop3.setBathrooms(3);
        prop3.setSquareFeet(3500.0);
        prop3.setYearBuilt(2022);
        prop3.setPropertyType("CONDO");
        prop3.setListingType("SALE");
        prop3.setAmenities(Arrays.asList("ocean_view", "private_beach", "spa", "elevator"));
        prop3.setLatitude(25.7617);
        prop3.setLongitude(-80.1918);
        prop3.setListedDate(LocalDateTime.now().minusDays(3));
        prop3.setAverageRating(4.9);
        prop3.setImageUrl("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=600&h=400");
        propertyRepository.save(prop3);

        Property prop4 = new Property();
        prop4.setTitle("Cozy Studio Apartment");
        prop4.setDescription("Perfect starter apartment in a vibrant neighborhood");
        prop4.setAddress("321 Elm Street");
        prop4.setCity("Boston");
        prop4.setState("MA");
        prop4.setZipCode("02101");
        prop4.setPrice(new BigDecimal("350000"));
        prop4.setBedrooms(1);
        prop4.setBathrooms(1);
        prop4.setSquareFeet(650.0);
        prop4.setYearBuilt(2010);
        prop4.setPropertyType("APARTMENT");
        prop4.setListingType("RENT");
        prop4.setAmenities(Arrays.asList("gym", "security", "parking"));
        prop4.setLatitude(42.3601);
        prop4.setLongitude(-71.0589);
        prop4.setListedDate(LocalDateTime.now().minusDays(1));
        prop4.setAverageRating(4.4);
        prop4.setImageUrl("https://images.unsplash.com/photo-1493857671505-72967e2e2760?w=600&h=400");
        propertyRepository.save(prop4);

        // Additional properties
        Property prop5 = new Property();
        prop5.setTitle("Urban Loft");
        prop5.setDescription("Trendy loft with exposed brick and high ceilings in vibrant neighborhood");
        prop5.setAddress("555 Industrial Way");
        prop5.setCity("Chicago");
        prop5.setState("IL");
        prop5.setZipCode("60608");
        prop5.setPrice(new BigDecimal("385000"));
        prop5.setBedrooms(2);
        prop5.setBathrooms(1);
        prop5.setSquareFeet(1100.0);
        prop5.setYearBuilt(2017);
        prop5.setPropertyType("LOFT");
        prop5.setListingType("SALE");
        prop5.setAmenities(Arrays.asList("high_ceiling", "brick_wall", "large_windows", "fitness_room"));
        prop5.setLatitude(41.8781);
        prop5.setLongitude(-87.6298);
        prop5.setListedDate(LocalDateTime.now().minusDays(7));
        prop5.setAverageRating(4.7);
        prop5.setImageUrl("https://images.unsplash.com/photo-1545291026-7eec264c27ff?w=600&h=400");
        propertyRepository.save(prop5);

        Property prop6 = new Property();
        prop6.setTitle("Mountain Retreat Cabin");
        prop6.setDescription("Secluded mountain cabin with fireplace and stunning views");
        prop6.setAddress("888 Mountain View Road");
        prop6.setCity("Denver");
        prop6.setState("CO");
        prop6.setZipCode("80202");
        prop6.setPrice(new BigDecimal("650000"));
        prop6.setBedrooms(3);
        prop6.setBathrooms(2);
        prop6.setSquareFeet(2200.0);
        prop6.setYearBuilt(2016);
        prop6.setPropertyType("CABIN");
        prop6.setListingType("SALE");
        prop6.setAmenities(Arrays.asList("fireplace", "mountain_view", "large_deck", "hot_tub"));
        prop6.setLatitude(39.7392);
        prop6.setLongitude(-104.9903);
        prop6.setListedDate(LocalDateTime.now().minusDays(14));
        prop6.setAverageRating(4.8);
        prop6.setImageUrl("https://images.unsplash.com/photo-1517457373614-b7152f800fd1?w=600&h=400");
        propertyRepository.save(prop6);

        System.out.println("✅ Sample properties initialized successfully! (6 properties with images and prices)");
    }
}
