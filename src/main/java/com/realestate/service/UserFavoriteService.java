package com.realestate.service;

import com.realestate.model.UserFavorite;
import com.realestate.repository.UserFavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserFavoriteService {

    @Autowired
    private UserFavoriteRepository favoriteRepository;

    /**
     * Get user's favorite properties
     */
    @Cacheable(value = "userFavorites", key = "#userId")
    public List<UserFavorite> getUserFavorites(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    /**
     * Add property to favorites
     */
    @CacheEvict(value = "userFavorites", allEntries = true)
    public UserFavorite addToFavorites(UserFavorite favorite) {
        return favoriteRepository.save(favorite);
    }

    /**
     * Check if property is favorited
     */
    public Boolean isFavorited(Long userId, Long propertyId) {
        return favoriteRepository.existsByUserIdAndPropertyId(userId, propertyId);
    }

    /**
     * Remove from favorites
     */
    @CacheEvict(value = "userFavorites", allEntries = true)
    public void removeFromFavorites(Long userId, Long propertyId) {
        favoriteRepository.findByUserIdAndPropertyId(userId, propertyId)
                .ifPresent(favorite -> favoriteRepository.delete(favorite));
    }

    /**
     * Get favorite count for user
     */
    public Long getFavoriteCount(Long userId) {
        return (long) favoriteRepository.findByUserId(userId).size();
    }
}
