# Real Estate Platform

A comprehensive real estate platform built with Spring Boot and AI/ML capabilities for property price prediction and recommendations.

## Project Structure

```
realestate-platform/
├── .vscode/                 # VS Code configuration
│   ├── settings.json       # Editor settings
│   ├── launch.json         # Debug configuration
│   └── tasks.json          # Build tasks
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/realestate/
│   │   │       ├── RealEstateApplication.java    # Main entry point
│   │   │       ├── controller/                   # REST API controllers
│   │   │       ├── service/                      # Business logic
│   │   │       ├── repository/                   # Data access layer
│   │   │       ├── model/                        # JPA entities
│   │   │       ├── config/                       # Spring configuration
│   │   │       └── ai/                           # AI/ML components
│   │   └── resources/
│   │       ├── application.yml                   # Main config
│   │       └── application-dev.yml               # Development config
│   └── test/                # Unit tests
├── pom.xml                  # Maven configuration
├── .env                     # Environment variables
├── api-test.http            # API test endpoints
└── README.md               # This file
```

## Technology Stack

- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **Database**: MySQL
- **Build Tool**: Maven
- **AI/ML**: DeepLearning4j, OpenAI GPT-3
- **Caching**: Spring Cache
- **Security**: Spring Security

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd realestate-platform
   ```

2. **Configure environment variables**
   ```bash
   cp .env .env.local
   # Edit .env.local with your configuration
   ```

3. **Create MySQL database**
   ```sql
   CREATE DATABASE realestate;
   CREATE DATABASE realestate_dev;
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   Or with development profile:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
   ```

## API Endpoints

### Property Management
- `GET /api/properties` - Get all properties
- `GET /api/properties/{id}` - Get property by ID
- `POST /api/properties` - Create new property
- `PUT /api/properties/{id}` - Update property
- `DELETE /api/properties/{id}` - Delete property

### AI Services
- `POST /api/ai/predict-price` - Predict property price using ML
- `GET /api/ai/recommendations` - Get property recommendations

## Testing

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=PropertyServiceTest
```

### API Testing with HTTP Client
Use the `api-test.http` file in VS Code with the REST Client extension:
1. Install "REST Client" extension
2. Open `api-test.http`
3. Click "Send Request" above each endpoint

## Configuration

### Application Properties

**application.yml** (Production)
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
  datasource:
    url: jdbc:mysql://localhost:3306/realestate
```

**application-dev.yml** (Development)
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
```

### Environment Variables

Key environment variables in `.env`:
- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` - Database connection
- `OPENAI_API_KEY` - OpenAI API key for price prediction
- `SPRING_PROFILES_ACTIVE` - Active Spring profile (dev/prod)

## Development

### In VS Code
1. Install Java Extension Pack
2. Debug configuration is already set up in `.vscode/launch.json`
3. Press `F5` to start debugging

### Build Tasks
- `maven: clean` - Clean build artifacts
- `maven: build` - Build the project
- `maven: test` - Run tests

## Project Features

- ✅ RESTful API for property management
- ✅ AI-powered price prediction
- ✅ Property recommendations engine
- ✅ User authentication & authorization
- ✅ Caching mechanism
- ✅ MySQL database integration
- ✅ Comprehensive logging
- ✅ Spring Security integration

## Contributing

1. Create a feature branch
2. Make your changes
3. Run tests: `mvn test`
4. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For issues and questions, please open an issue on the GitHub repository.

## Authors

- Real Estate Platform Team

## Changelog

### Version 1.0.0
- Initial release
- Basic property management CRUD operations
- AI price prediction integration
- MySQL database integration
