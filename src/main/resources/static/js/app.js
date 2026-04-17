const API_BASE_URL = '/api';
let allProperties = [];
let currentFilter = 'ALL';

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    loadProperties();
});

/**
 * Load all properties from the API
 */
function loadProperties() {
    fetch(`${API_BASE_URL}/properties`)
        .then(response => response.json())
        .then(data => {
            allProperties = data;
            displayProperties(allProperties);
        })
        .catch(error => {
            console.error('Error loading properties:', error);
            displayError('Failed to load properties');
        });
}

/**
 * Display properties in the grid
 */
function displayProperties(properties) {
    const grid = document.getElementById('propertiesGrid');
    
    if (properties.length === 0) {
        grid.innerHTML = '<p style="grid-column: 1/-1; text-align: center;">No properties found</p>';
        return;
    }

    grid.innerHTML = properties.map(property => `
        <div class="property-card" onclick="openPropertyModal(${property.id})">
            <div class="property-image" style="background: white; overflow: hidden;">
                ${property.imageUrl ? `
                    <img src="${property.imageUrl}" alt="${property.title}" style="width: 100%; height: 100%; object-fit: cover;">
                ` : `
                    <div style="width: 100%; height: 100%; background: linear-gradient(135deg, #2E86AB, #A23B72); display: flex; align-items: center; justify-content: center; color: white; font-size: 48px;">
                        <i class="fas fa-home"></i>
                    </div>
                `}
                <span class="property-badge">${property.listingType}</span>
            </div>
            <div class="property-info">
                <div class="property-title">${property.title}</div>
                <div class="property-location">
                    <i class="fas fa-map-marker-alt"></i>
                    ${property.city}, ${property.state}
                </div>
                <div class="property-details">
                    <div class="detail-item">
                        <i class="fas fa-bed"></i>
                        ${property.bedrooms} Bed
                    </div>
                    <div class="detail-item">
                        <i class="fas fa-bath"></i>
                        ${property.bathrooms} Bath
                    </div>
                    <div class="detail-item">
                        <i class="fas fa-ruler-combined"></i>
                        ${property.squareFeet?.toLocaleString() || 'N/A'} sqft
                    </div>
                </div>
                <div class="property-price">
                    $${property.price?.toLocaleString()}
                </div>
                ${property.averageRating ? `
                    <div class="property-rating">
                        <i class="fas fa-star"></i>
                        ${property.averageRating.toFixed(1)} (${Math.round(Math.random() * 50) + 10} reviews)
                    </div>
                ` : ''}
                <button class="property-btn" onclick="event.stopPropagation(); viewDetails(${property.id})">
                    View Details
                </button>
            </div>
        </div>
    `).join('');
}

/**
 * Filter properties by type
 */
function filterByType(type) {
    currentFilter = type;
    
    // Update active button
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    event.target.classList.add('active');

    // Filter and display
    const filtered = type === 'ALL' 
        ? allProperties 
        : allProperties.filter(p => p.propertyType === type);
    
    displayProperties(filtered);
}

/**
 * Search properties with filters
 */
function searchProperties() {
    const city = document.getElementById('searchCity').value;
    const maxPrice = document.getElementById('searchPrice').value;
    const minBedrooms = document.getElementById('searchBedrooms').value;

    const params = new URLSearchParams();
    if (city) params.append('city', city);
    if (maxPrice) params.append('maxPrice', maxPrice);
    if (minBedrooms) params.append('minBedrooms', minBedrooms);

    const url = params.toString() 
        ? `${API_BASE_URL}/properties/search?${params}`
        : `${API_BASE_URL}/properties`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            allProperties = data;
            displayProperties(data);
        })
        .catch(error => {
            console.error('Search error:', error);
            displayError('Search failed');
        });
}

/**
 * Open property details in modal
 */
function openPropertyModal(propertyId) {
    fetch(`${API_BASE_URL}/properties/${propertyId}`)
        .then(response => response.json())
        .then(property => {
            const modal = document.getElementById('propertyModal');
            const modalBody = document.getElementById('modalBody');

            modalBody.innerHTML = `
                ${property.imageUrl ? `
                    <div style="margin-bottom: 20px; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);">
                        <img src="${property.imageUrl}" alt="${property.title}" style="width: 100%; height: 400px; object-fit: cover;">
                    </div>
                ` : `
                    <div style="background: linear-gradient(135deg, #2E86AB, #A23B72); height: 300px; border-radius: 10px; display: flex; align-items: center; justify-content: center; color: white; margin-bottom: 20px;">
                        <i class="fas fa-home" style="font-size: 80px;"></i>
                    </div>
                `}
                
                <div style="margin-bottom: 20px;">
                    <h2 style="margin-bottom: 10px;">${property.title}</h2>
                    <p style="color: #666; margin-bottom: 15px;">
                        <i class="fas fa-map-marker-alt"></i> 
                        ${property.address}, ${property.city}, ${property.state} ${property.zipCode}
                    </p>
                </div>

                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-bottom: 20px;">
                    <div style="background: #f0f0f0; padding: 15px; border-radius: 5px;">
                        <div style="font-size: 12px; color: #666;">Bedrooms</div>
                        <div style="font-size: 24px; font-weight: bold; color: #2E86AB;">${property.bedrooms}</div>
                    </div>
                    <div style="background: #f0f0f0; padding: 15px; border-radius: 5px;">
                        <div style="font-size: 12px; color: #666;">Bathrooms</div>
                        <div style="font-size: 24px; font-weight: bold; color: #2E86AB;">${property.bathrooms}</div>
                    </div>
                    <div style="background: #f0f0f0; padding: 15px; border-radius: 5px;">
                        <div style="font-size: 12px; color: #666;">Square Feet</div>
                        <div style="font-size: 24px; font-weight: bold; color: #2E86AB;">${property.squareFeet?.toLocaleString()}</div>
                    </div>
                    <div style="background: #f0f0f0; padding: 15px; border-radius: 5px;">
                        <div style="font-size: 12px; color: #666;">Year Built</div>
                        <div style="font-size: 24px; font-weight: bold; color: #2E86AB;">${property.yearBuilt}</div>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <h3 style="margin-bottom: 10px;">Description</h3>
                    <p style="color: #666; line-height: 1.6;">${property.description}</p>
                </div>

                <div style="margin-bottom: 20px;">
                    <h3 style="margin-bottom: 10px;">Amenities</h3>
                    <div style="display: flex; flex-wrap: wrap; gap: 10px;">
                        ${property.amenities?.map(amenity => `
                            <span style="background: #e8f4f8; color: #2E86AB; padding: 5px 12px; border-radius: 20px; font-size: 12px;">
                                <i class="fas fa-check"></i> ${amenity}
                            </span>
                        `).join('') || '<p>No amenities listed</p>'}
                    </div>
                </div>

                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px;">
                    <div style="background: #f0f0f0; padding: 20px; border-radius: 5px; text-align: center;">
                        <div style="font-size: 12px; color: #666; margin-bottom: 5px;">Listed Price</div>
                        <div style="font-size: 28px; font-weight: bold; color: #2E86AB;">
                            $${property.price?.toLocaleString()}
                        </div>
                    </div>
                    ${property.averageRating ? `
                        <div style="background: #f0f0f0; padding: 20px; border-radius: 5px; text-align: center;">
                            <div style="font-size: 12px; color: #666; margin-bottom: 5px;">Rating</div>
                            <div style="font-size: 28px; font-weight: bold; color: #F18F01;">
                                <i class="fas fa-star"></i> ${property.averageRating.toFixed(1)}
                            </div>
                        </div>
                    ` : ''}
                </div>

                <div style="margin-top: 20px; display: flex; gap: 10px;">
                    <button onclick="predictPrice(${propertyId})" style="flex: 1; padding: 12px; background: #06A77D; color: white; border: none; border-radius: 5px; cursor: pointer; font-weight: bold;">
                        <i class="fas fa-brain"></i> AI Price Prediction
                    </button>
                    <button onclick="addToFavorites(${propertyId})" style="flex: 1; padding: 12px; background: #A23B72; color: white; border: none; border-radius: 5px; cursor: pointer; font-weight: bold;">
                        <i class="fas fa-heart"></i> Add to Favorites
                    </button>
                </div>
            `;

            modal.style.display = 'block';
        })
        .catch(error => {
            console.error('Error loading property details:', error);
            displayError('Failed to load property details');
        });
}

/**
 * Close the modal
 */
function closeModal() {
    document.getElementById('propertyModal').style.display = 'none';
}

/**
 * View full property details
 */
function viewDetails(propertyId) {
    openPropertyModal(propertyId);
}

/**
 * Predict property price using AI
 */
function predictPrice(propertyId) {
    const property = allProperties.find(p => p.id === propertyId);
    
    if (!property) return;

    fetch(`${API_BASE_URL}/ai/predict-price`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(property)
    })
    .then(response => response.json())
    .then(data => {
        const predictedPrice = typeof data === 'object' ? data.predictedPrice : data;
        alert(`AI Predicted Price: $${Math.round(predictedPrice).toLocaleString()}`);
    })
    .catch(error => {
        console.error('Price prediction error:', error);
        displayError('Could not predict price');
    });
}

/**
 * Add property to favorites
 */
function addToFavorites(propertyId) {
    alert(`Property ${propertyId} added to favorites!`);
    // This would typically make an API call to save to user's favorites
}

/**
 * Submit contact form
 */
function submitContact(event) {
    event.preventDefault();
    alert('Thank you for your message! We will contact you soon.');
    event.target.reset();
}

/**
 * Display error message
 */
function displayError(message) {
    alert(`Error: ${message}`);
}

// Close modal when clicking outside
window.onclick = function(event) {
    const modal = document.getElementById('propertyModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}
