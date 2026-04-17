package com.realestate.repository;

import com.realestate.model.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {
    
    /**
     * Find all favorites for a user
     */
    List<UserFavorite> findByUserId(Long userId);
    
    /**
     * Find a specific favorite
     */
    Optional<UserFavorite> findByUserIdAndPropertyId(Long userId, Long propertyId);
    
    /**
     * Check if property is favorited by user
     */
    Boolean existsByUserIdAndPropertyId(Long userId, Long propertyId);
}
