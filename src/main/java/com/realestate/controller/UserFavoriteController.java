package com.realestate.controller;

import com.realestate.model.UserFavorite;
import com.realestate.service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserFavoriteController {

    @Autowired
    private UserFavoriteService favoriteService;

    /**
     * Get all favorites for a user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserFavorite>> getUserFavorites(@PathVariable Long userId) {
        List<UserFavorite> favorites = favoriteService.getUserFavorites(userId);
        return ResponseEntity.ok(favorites);
    }

    /**
     * Add property to favorites
     */
    @PostMapping
    public ResponseEntity<?> addToFavorites(@RequestBody UserFavorite favorite) {
        try {
            UserFavorite saved = favoriteService.addToFavorites(favorite);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error adding to favorites: " + e.getMessage());
        }
    }

    /**
     * Check if property is in favorites
     */
    @GetMapping("/user/{userId}/property/{propertyId}")
    public ResponseEntity<Boolean> isFavorited(
            @PathVariable Long userId,
            @PathVariable Long propertyId) {
        Boolean isFavorited = favoriteService.isFavorited(userId, propertyId);
        return ResponseEntity.ok(isFavorited);
    }

    /**
     * Remove from favorites
     */
    @DeleteMapping("/user/{userId}/property/{propertyId}")
    public ResponseEntity<?> removeFromFavorites(
            @PathVariable Long userId,
            @PathVariable Long propertyId) {
        try {
            favoriteService.removeFromFavorites(userId, propertyId);
            return ResponseEntity.ok("Removed from favorites");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Favorite not found");
        }
    }

    /**
     * Get favorite count for user
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<?> getFavoriteCount(@PathVariable Long userId) {
        long count = favoriteService.getFavoriteCount(userId);
        return ResponseEntity.ok("Favorite Count: " + count);
    }
}
