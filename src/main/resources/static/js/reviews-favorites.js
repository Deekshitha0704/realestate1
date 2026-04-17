/**
 * Enhanced API module with review and favorites functionality
 */

// Get reviews for a property
async function getPropertyReviews(propertyId) {
    try {
        const response = await fetch(`/api/reviews/property/${propertyId}`);
        const reviews = await response.json();
        displayReviews(reviews, propertyId);
    } catch (error) {
        console.error('Error loading reviews:', error);
    }
}

// Display reviews in modal
function displayReviews(reviews, propertyId) {
    const reviewsHtml = reviews.length > 0 ? `
        <div style="margin-top: 30px;">
            <h3>Customer Reviews (${reviews.length})</h3>
            <div style="margin-top: 15px;">
                ${reviews.map(review => `
                    <div style="border-bottom: 1px solid #eee; padding: 15px 0;">
                        <div style="display: flex; justify-content: space-between; margin-bottom: 10px;">
                            <strong>${review.reviewTitle}</strong>
                            <span style="color: #F18F01;">
                                ${'★'.repeat(review.rating)}${'☆'.repeat(5-review.rating)}
                            </span>
                        </div>
                        <p style="color: #666; margin-bottom: 10px;">${review.reviewText}</p>
                        <small style="color: #999;">By User ${review.user?.id} • ${new Date(review.reviewDate).toLocaleDateString()}</small>
                        ${review.isVerifiedPurchase ? '<br><span style="color: #06A77D; font-size: 12px;">✓ Verified Purchase</span>' : ''}
                    </div>
                `).join('')}
            </div>
        </div>
    ` : `
        <p style="text-align: center; color: #999; margin-top: 20px;">No reviews yet. Be the first to review!</p>
    `;
    
    const reviewsContainer = document.getElementById('reviewsContainer') || 
        document.createElement('div');
    reviewsContainer.id = 'reviewsContainer';
    reviewsContainer.innerHTML = reviewsHtml;
    
    const modalBody = document.getElementById('modalBody');
    if (modalBody) {
        modalBody.appendChild(reviewsContainer);
    }
}

// Add property to favorites
async function addPropertyToFavorites(userId, propertyId) {
    try {
        const response = await fetch('/api/favorites', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                user: { id: userId },
                property: { id: propertyId }
            })
        });
        
        if (response.ok) {
            alert('Added to favorites!');
            updateFavoriteButton(propertyId, true);
        }
    } catch (error) {
        console.error('Error adding to favorites:', error);
    }
}

// Remove property from favorites
async function removePropertyFromFavorites(userId, propertyId) {
    try {
        const response = await fetch(`/api/favorites/user/${userId}/property/${propertyId}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            alert('Removed from favorites!');
            updateFavoriteButton(propertyId, false);
        }
    } catch (error) {
        console.error('Error removing from favorites:', error);
    }
}

// Check if property is favorited
async function checkIfFavorited(userId, propertyId) {
    try {
        const response = await fetch(`/api/favorites/user/${userId}/property/${propertyId}`);
        const isFavorited = await response.json();
        updateFavoriteButton(propertyId, isFavorited);
    } catch (error) {
        console.error('Error checking favorite status:', error);
    }
}

// Update favorite button UI
function updateFavoriteButton(propertyId, isFavorited) {
    const button = document.querySelector(`[data-property-id="${propertyId}"] .favorite-btn`);
    if (button) {
        if (isFavorited) {
            button.classList.add('favorited');
            button.innerHTML = '<i class="fas fa-heart"></i> Favorited';
        } else {
            button.classList.remove('favorited');
            button.innerHTML = '<i class="far fa-heart"></i> Add to Favorites';
        }
    }
}

// Submit property review
async function submitPropertyReview(propertyId, userId, rating, title, reviewText) {
    try {
        const response = await fetch('/api/reviews', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                property: { id: propertyId },
                user: { id: userId },
                rating: rating,
                reviewTitle: title,
                reviewText: reviewText,
                isVerifiedPurchase: true
            })
        });
        
        if (response.ok) {
            alert('Review submitted successfully!');
            // Reload reviews
            getPropertyReviews(propertyId);
        }
    } catch (error) {
        console.error('Error submitting review:', error);
    }
}

// Get user's favorite properties
async function getUserFavorites(userId) {
    try {
        const response = await fetch(`/api/favorites/user/${userId}`);
        const favorites = await response.json();
        displayUserFavorites(favorites);
    } catch (error) {
        console.error('Error loading favorites:', error);
    }
}

// Display user's favorite properties
function displayUserFavorites(favorites) {
    const grid = document.getElementById('favoritesGrid') || 
        document.createElement('div');
    grid.id = 'favoritesGrid';
    grid.className = 'properties-grid';
    
    if (favorites.length === 0) {
        grid.innerHTML = '<p style="grid-column: 1/-1; text-align: center;">No favorite properties yet</p>';
        return;
    }
    
    grid.innerHTML = favorites.map(fav => `
        <div class="property-card" data-property-id="${fav.property.id}" onclick="openPropertyModal(${fav.property.id})">
            <div class="property-image">
                <i class="fas fa-home"></i>
            </div>
            <div class="property-info">
                <div class="property-title">${fav.property.title}</div>
                <div class="property-location">
                    <i class="fas fa-map-marker-alt"></i>
                    ${fav.property.city}, ${fav.property.state}
                </div>
                <div class="property-price">
                    $${fav.property.price?.toLocaleString()}
                </div>
                ${fav.notes ? `<p style="color: #666; font-size: 12px; margin: 10px 0;">Note: ${fav.notes}</p>` : ''}
            </div>
        </div>
    `).join('');
}

// Get average property rating
async function getPropertyAverageRating(propertyId) {
    try {
        const response = await fetch(`/api/reviews/property/${propertyId}/average-rating`);
        const data = await response.json();
        return parseFloat(data);
    } catch (error) {
        console.error('Error getting average rating:', error);
        return 0;
    }
}
