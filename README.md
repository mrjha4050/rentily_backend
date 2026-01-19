# Rentily Backend

Spring Boot backend application for Rentily.

## Prerequisites

Before running this project, ensure you have the following installed:

- **Java 21** (JDK 21)
- **Maven 3.6+** (or use the included Maven wrapper)
- **MongoDB** (running on localhost:27017)
- (Optional) **Kafka** - Required if using chat functionality

## Setup Instructions

### 1. Install Java 21

Download and install Java 21 from [Oracle](https://www.oracle.com/java/technologies/downloads/#java21) or [OpenJDK](https://adoptium.net/).

Verify installation:
```bash
java -version
```

### 2. Install and Start MongoDB

**Windows:**
- Download MongoDB from [MongoDB Download Center](https://www.mongodb.com/try/download/community)
- Install and start MongoDB service
- Or use MongoDB Atlas (cloud) and update connection string in `application.properties`

**macOS (using Homebrew):**
```bash
brew tap mongodb/brew
brew install mongodb-community
brew services start mongodb-community
```

**Linux:**
```bash
sudo systemctl start mongodb
# or
sudo service mongodb start
```

Verify MongoDB is running:
```bash
# Windows PowerShell
netstat -an | findstr 27017

# macOS/Linux
netstat -an | grep 27017
# or
mongosh --eval "db.version()"
```

### 3. (Optional) Environment Variables

The application uses `java-dotenv` to load environment variables from a `.env` file. Create a `.env` file in the project root if needed:

```env
# Example .env file (create if you need to override default values)
# MongoDB connection (if using remote/cloud MongoDB)
# SPRING_DATA_MONGODB_URI=mongodb://username:password@host:port/database

# JWT Secret (if you want to use environment variable instead of hardcoded)
# JWT_SECRET=your-secret-key

# Cloudinary (already configured in application.properties, but can override)
# CLOUDINARY_CLOUD_NAME=your-cloud-name
# CLOUDINARY_API_KEY=your-api-key
# CLOUDINARY_API_SECRET=your-api-secret
```

**Note:** The application will still run without a `.env` file as it uses values from `application.properties` by default.

### 4. (Optional) Kafka Setup

If you're using chat functionality, you'll need Kafka running:

**Windows:**
- Download Kafka from [Apache Kafka](https://kafka.apache.org/downloads)
- Extract and follow Kafka documentation for Windows setup

**macOS/Linux:**
- Use the provided script (requires Kafka to be downloaded):
```bash
chmod +x script/kafka-setup.sh
./script/kafka-setup.sh
```

## Running the Application

### Option 1: Using Maven Wrapper (Recommended)

**Windows:**
```cmd
.\mvnw.cmd spring-boot:run
```

**macOS/Linux:**
```bash
./mvnw spring-boot:run
```

### Option 2: Using System Maven

```bash
mvn spring-boot:run
```

### Option 3: Build and Run JAR

Build the project:
```bash
# Windows
.\mvnw.cmd clean package

# macOS/Linux
./mvnw clean package
```

Run the JAR:
```bash
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

## Configuration

The application is configured via `src/main/resources/application.properties`:

- **MongoDB:** `localhost:27017` (database: `backend`)
- **Server Port:** Default `8080` (Spring Boot default)
- **Cloudinary:** Configured for image uploads
- **File Upload:** Max 10MB per file, 20MB total request size

## API Documentation

Once the application is running, access Swagger UI at:
- http://localhost:8080/swagger-ui.html
- http://localhost:8080/swagger-ui/index.html

API Docs (OpenAPI JSON):
- http://localhost:8080/v3/api-docs

## API Endpoints

The following endpoints are publicly accessible (no authentication required):
- `/api/auth/**` - Authentication endpoints
- `/api/products/**` - Product endpoints
- `/api/cart/**` - Cart endpoints
- `/api/transaction/**` - Transaction endpoints
- `/api/chats/**` - Chat endpoints
- `/api/notifications/**` - Notification endpoints
- `/swagger-ui/**` - Swagger documentation
- `/v3/api-docs/**` - OpenAPI documentation

Other endpoints require JWT authentication.

## Troubleshooting

### MongoDB Connection Error
- Ensure MongoDB is running on `localhost:27017`
- Check MongoDB logs for connection issues
- Verify MongoDB service is started

### Port Already in Use
- Change the port in `application.properties`:
  ```properties
  server.port=8081
  ```

### Java Version Error
- Verify Java 21 is installed: `java -version`
- Set `JAVA_HOME` environment variable if needed

### Build Errors
- Clean and rebuild: `./mvnw clean install`
- Check Maven is properly configured
- Ensure all dependencies can be downloaded

## Development

### Project Structure
```
src/
├── main/
│   ├── java/com/backend/backend/
│   │   ├── config/          # Security, MongoDB, JWT configuration
│   │   ├── Controller/       # REST controllers
│   │   ├── Service/          # Business logic
│   │   ├── dao/              # Repository interfaces
│   │   ├── models/           # Entity models
│   │   └── dto/              # Data Transfer Objects
│   └── resources/
│       └── application.properties
└── test/                     # Test files
```

### Technologies Used
- Spring Boot 3.4.5
- Spring Data MongoDB
- Spring Security
- JWT Authentication
- Cloudinary (Image Upload)
- Lombok
- SpringDoc OpenAPI (Swagger)

## License

[Add your license information here]