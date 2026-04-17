# API Documentation - Real Estate Platform

## Base URL
```
http://localhost:8080/api
```

## Properties Endpoints

### Get All Properties
```
GET /properties
Parameters:
  - city (optional): Filter by city
  - propertyType (optional): Filter by property type
Response: List<Property>
```

### Get Property by ID
```
GET /properties/{id}
Response: Property object
```

### Create Property
```
POST /properties
Body: Property object
Response: Created Property
```

### Update Property
```
PUT /properties/{id}
Body: Property object
Response: Updated Property
```

### Delete Property
```
DELETE /properties/{id}
Response: Success message
```

### Search Properties
```
GET /properties/search
Parameters:
  - city (optional)
  - minPrice (optional)
  - maxPrice (optional)
  - minBedrooms (optional)
Response: List<Property>
```

## AI Endpoints

### Get Recommendations
```
POST /ai/recommendations
Body: AIRecommendationRequest
Response: List<Property>
```

### Predict Price
```
POST /ai/predict-price
Body: Property object
Response: {
  propertyId: number,
  actualPrice: number,
  predictedPrice: number,
  variance: number
}
```

### Get Trending Properties
```
GET /ai/trending
Parameters:
  - limit (optional, default: 10)
Response: List<Property>
```

### Get Similar Properties
```
GET /ai/similar/{propertyId}
Parameters:
  - limit (optional, default: 5)
Response: List<Property>
```

## Reviews Endpoints

### Get Property Reviews
```
GET /reviews/property/{propertyId}
Parameters:
  - verifiedOnly (optional)
Response: List<PropertyReview>
```

### Get User Reviews
```
GET /reviews/user/{userId}
Response: List<PropertyReview>
```

### Create Review
```
POST /reviews
Body: PropertyReview object
Response: Created PropertyReview
```

### Update Review
```
PUT /reviews/{reviewId}
Body: PropertyReview object
Response: Updated PropertyReview
```

### Delete Review
```
DELETE /reviews/{reviewId}
Response: Success message
```

### Get Average Rating
```
GET /reviews/property/{propertyId}/average-rating
Response: Average rating value
```

## Favorites Endpoints

### Get User Favorites
```
GET /favorites/user/{userId}
Response: List<UserFavorite>
```

### Add to Favorites
```
POST /favorites
Body: UserFavorite object
Response: Created UserFavorite
```

### Check if Favorited
```
GET /favorites/user/{userId}/property/{propertyId}
Response: Boolean
```

### Remove from Favorites
```
DELETE /favorites/user/{userId}/property/{propertyId}
Response: Success message
```

### Get Favorite Count
```
GET /favorites/user/{userId}/count
Response: Favorite count
```

## Error Responses

All endpoints return error responses in the following format:
```json
{
  "error": "Error message",
  "status": 400,
  "timestamp": 1629840000000
}
```

## Authentication
Currently, no authentication is required. In production, implement JWT or OAuth2.

## Rate Limiting
No rate limiting currently implemented. Consider implementing in production.

## Pagination
Currently, pagination is not implemented. All results are returned as lists.

## Caching
- Properties: Cached for 1 hour
- Reviews: Cached for 30 minutes
- Favorites: Cached for 1 hour
