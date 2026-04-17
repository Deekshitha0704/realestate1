-- Real Estate Platform Database Schema (H2 Compatible)

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(50),
    profile_image_url VARCHAR(500),
    bio TEXT,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login_date TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    is_verified BOOLEAN DEFAULT FALSE
);

-- Properties Table
CREATE TABLE IF NOT EXISTS properties (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50),
    zip_code VARCHAR(10),
    price DECIMAL(15, 2) NOT NULL,
    bedrooms INT,
    bathrooms INT,
    square_feet DOUBLE,
    year_built INT,
    property_type VARCHAR(50),
    listing_type VARCHAR(50),
    latitude DOUBLE,
    longitude DOUBLE,
    listed_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_available BOOLEAN DEFAULT TRUE,
    average_rating DOUBLE DEFAULT 0,
    feature_vector VARCHAR(1000)
);

-- Property Amenities Table
CREATE TABLE IF NOT EXISTS property_amenities (
    property_id BIGINT NOT NULL,
    amenities VARCHAR(100),
    FOREIGN KEY (property_id) REFERENCES properties(id) ON DELETE CASCADE
);

-- User Property Favorites
CREATE TABLE IF NOT EXISTS user_favorites (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (property_id) REFERENCES properties(id) ON DELETE CASCADE,
    UNIQUE (user_id, property_id)
);

-- Property Reviews
CREATE TABLE IF NOT EXISTS property_reviews (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    property_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating INT,
    review_text TEXT,
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (property_id) REFERENCES properties(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_user_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_user_type ON users(user_type);
CREATE INDEX IF NOT EXISTS idx_city ON properties(city);
CREATE INDEX IF NOT EXISTS idx_price ON properties(price);
CREATE INDEX IF NOT EXISTS idx_property_type ON properties(property_type);
CREATE INDEX IF NOT EXISTS idx_is_available ON properties(is_available);
CREATE INDEX idx_property_city_price ON properties(city, price);
CREATE INDEX idx_property_bedrooms ON properties(bedrooms);
CREATE INDEX idx_favorites_user ON user_favorites(user_id);
CREATE INDEX idx_reviews_property ON property_reviews(property_id);
CREATE INDEX idx_reviews_rating ON property_reviews(rating);
