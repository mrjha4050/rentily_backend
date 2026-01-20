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

All endpoints are publicly accessible (no authentication required). However, some endpoints may use JWT tokens from the Authorization header for user identification.

### Authentication Endpoints (`/api/auth`)

#### Register User
- **POST** `/api/auth/register`
- **Description:** Register a new user
- **Request Body:**
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string",
    "role": "string",
    "phoneNumber": "string"
  }
  ```
- **Response:** 
  - `200 OK`: "Registration successful"
  - `400 Bad Request`: "User already exists"

#### Login
- **POST** `/api/auth/login`
- **Description:** Login with email and password
- **Request Body:**
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response:**
  - `200 OK`: `{ "token": "jwt_token_string" }`
  - `401 Unauthorized`: "Invalid credentials"

#### Get Current User
- **GET** `/api/auth/me`
- **Description:** Get current user information
- **Headers:** `Authorization: Bearer <token>`
- **Response:**
  - `200 OK`:
    ```json
    {
      "id": "string",
      "name": "string",
      "email": "string",
      "role": "string"
    }
    ```
  - `401 Unauthorized`: "Invalid credentials"

#### Logout
- **POST** `/api/auth/logout`
- **Description:** Logout current user
- **Headers:** `Authorization: Bearer <token>`
- **Response:** `200 OK`: "Logout successful"

#### Reset Password
- **POST** `/api/auth/reset-Password`
- **Description:** Reset user password
- **Request Body:**
  ```json
  {
    "email": "string",
    "newPassword": "string"
  }
  ```
- **Response:**
  - `200 OK`: "Password reset successful"
  - `400 Bad Request`: "Invalid credentials"

---

### Product Endpoints (`/api/products`)

#### Create Product
- **POST** `/api/products`
- **Description:** Create a new product
- **Content-Type:** `multipart/form-data`
- **Request:**
  - `product` (JSON): ProductDTO object
  - `images` (File[], optional): Array of image files
- **Request Body (product):**
  ```json
  {
    "title": "string",
    "description": "string",
    "price": 0.0,
    "category": "string",
    "type": "string (SELL or RENT)",
    "status": "string (AVAILABLE, SOLD, RENTED)",
    "userId": "string",
    "imageUrls": ["string"]
  }
  ```
- **Response:** `200 OK` - Product object

#### Update Product
- **PUT** `/api/products/{id}`
- **Description:** Update an existing product
- **Content-Type:** `multipart/form-data`
- **Path Parameters:** `id` (string) - Product ID
- **Request:**
  - `product` (JSON): ProductDTO object
  - `image` (File[], optional): Array of image files
- **Response:** `200 OK` - Product object

#### Get Product by ID
- **GET** `/api/products/{id}`
- **Description:** Get a product by its ID
- **Path Parameters:** `id` (string) - Product ID
- **Query Parameters:**
  - `page` (int, default: 0) - Page number
  - `size` (int, default: 10) - Page size
- **Response:** `200 OK` - Product object

#### Delete Product
- **DELETE** `/api/products/{id}`
- **Description:** Delete a product
- **Path Parameters:** `id` (string) - Product ID
- **Response:** `204 No Content`

#### Get Products by User
- **GET** `/api/products/user/{userId}`
- **Description:** Get all products for a specific user
- **Path Parameters:** `userId` (string) - User ID
- **Query Parameters:**
  - `page` (int, default: 0) - Page number
  - `size` (int, default: 10) - Page size
- **Response:** `200 OK` - Page<Product>

#### Get Products by Category
- **GET** `/api/products/category/{category}`
- **Description:** Get products filtered by category
- **Path Parameters:** `category` (string) - Category name
- **Query Parameters:**
  - `page` (int, default: 0) - Page number
  - `size` (int, default: 10) - Page size
- **Response:** `200 OK` - Page<Product>

#### Get Products by Type
- **GET** `/api/products/type/{type}`
- **Description:** Get products filtered by type (SELL or RENT)
- **Path Parameters:** `type` (string) - Product type
- **Query Parameters:**
  - `page` (int, default: 0) - Page number
  - `size` (int, default: 10) - Page size
- **Response:** `200 OK` - Page<Product>

#### Filter Products by Category and Type
- **GET** `/api/products/filter`
- **Description:** Get products filtered by category and type
- **Query Parameters:**
  - `category` (string, required) - Category name
  - `type` (string, required) - Product type
  - `page` (int, default: 0) - Page number
  - `size` (int, default: 10) - Page size
- **Response:** `200 OK` - Page<Product>

#### Search Products
- **GET** `/api/products/search`
- **Description:** Advanced product search with filters
- **Query Parameters:**
  - `keyword` (string, default: "") - Search keyword
  - `category` (string, default: "") - Category filter
  - `type` (string, default: "") - Type filter
  - `minPrice` (double, default: 0) - Minimum price
  - `maxPrice` (double, default: 1000000) - Maximum price
  - `page` (int, default: 0) - Page number
  - `size` (int, default: 10) - Page size
- **Response:** `200 OK` - Page<Product>

#### Get Recent Products
- **GET** `/api/products/recent`
- **Description:** Get most recently added products
- **Query Parameters:**
  - `limit` (int, default: 10) - Maximum number of products
- **Response:** `200 OK` - List<Product>

---

### Transaction Endpoints (`/api/transaction`)

#### Create Transaction
- **POST** `/api/transaction/create`
- **Description:** Create a new transaction
- **Request Body:**
  ```json
  {
    "productId": "string",
    "sellerId": "string",
    "buyerId": "string",
    "type": "string (SELL, RENT, BUY)",
    "status": "string (PENDING, COMPLETED, CANCELLED)",
    "timestamp": "datetime"
  }
  ```
- **Response:** `200 OK` - Transaction object

#### Get Transactions by Buyer
- **GET** `/api/transaction/buyer/{buyerId}`
- **Description:** Get all transactions for a buyer
- **Path Parameters:** `buyerId` (string) - Buyer user ID
- **Response:** `200 OK` - Iterable<Transaction>

#### Get Transactions by Seller
- **GET** `/api/transaction/seller/{sellerId}`
- **Description:** Get all transactions for a seller
- **Path Parameters:** `sellerId` (string) - Seller user ID
- **Response:** `200 OK` - Iterable<Transaction>

#### Update Transaction Status
- **PATCH** `/api/transaction/{transactionId}/status`
- **Description:** Update transaction status
- **Path Parameters:** `transactionId` (string) - Transaction ID
- **Query Parameters:** `status` (string, required) - "COMPLETE" or "CANCELLED"
- **Response:** `200 OK` - Transaction object

---

### Review Endpoints (`/api/reviews`)

#### Submit Review
- **POST** `/api/reviews/{reviewerId}`
- **Description:** Submit a review for a seller
- **Path Parameters:** `reviewerId` (string) - ID of the reviewer
- **Request Body:**
  ```json
  {
    "sellerId": "string",
    "productId": "string",
    "rating": 1-5,
    "comment": "string"
  }
  ```
- **Response:** `200 OK` - Review object

#### Get Reviews by Seller
- **GET** `/api/reviews/seller/{sellerId}`
- **Description:** Get all reviews for a seller
- **Path Parameters:** `sellerId` (string) - Seller user ID
- **Response:** `200 OK` - List<Review>

#### Get Average Rating
- **GET** `/api/reviews/average/{sellerId}/average`
- **Description:** Get average rating for a seller
- **Path Parameters:** `sellerId` (string) - Seller user ID
- **Response:** `200 OK` - Double (average rating)

---

### Chat Endpoints (`/api/chats`)

#### Create or Get Chat Room
- **POST** `/api/chats/room`
- **Description:** Create a new chat room or get existing one
- **Query Parameters:**
  - `productId` (string, required) - Product ID
  - `buyerId` (string, required) - Buyer user ID
  - `sellerId` (string, required) - Seller user ID
- **Response:** `200 OK` - ChatRoom object

#### Get Chat Room
- **GET** `/api/chats/room`
- **Description:** Get a chat room by product and buyer
- **Query Parameters:**
  - `productId` (string, required) - Product ID
  - `buyerId` (string, required) - Buyer user ID
- **Response:** `200 OK` - ChatRoom object

#### Delete Chat Room
- **DELETE** `/api/chats/room`
- **Description:** Delete a chat room
- **Query Parameters:**
  - `productId` (string, required) - Product ID
  - `buyerId` (string, required) - Buyer user ID
- **Response:** `204 No Content`

#### Get User Chat Rooms
- **GET** `/api/chats/rooms/{userId}`
- **Description:** Get all chat rooms for a user
- **Path Parameters:** `userId` (string) - User ID
- **Response:** `200 OK` - List<ChatRoom>

#### Send Message
- **POST** `/api/chats/message`
- **Description:** Send a message in a chat room
- **Request Body:**
  ```json
  {
    "chatRoomId": "string",
    "senderId": "string",
    "receiverId": "string",
    "content": "string",
    "timestamp": "datetime"
  }
  ```
- **Response:** `200 OK` - ChatMessage object

#### Get Messages
- **GET** `/api/chats/messages/{chatRoomId}`
- **Description:** Get all messages in a chat room
- **Path Parameters:** `chatRoomId` (string) - Chat room ID
- **Response:** `200 OK` - List<ChatMessage>

---

### Notification Endpoints (`/api/notifications`)

#### Send Notification
- **POST** `/api/notifications/send`
- **Description:** Send a push notification
- **Request Body:**
  ```json
  {
    "to": "string",
    "title": "string",
    "body": "string"
  }
  ```
- **Response:** `200 OK` - String response

---

### Health Endpoint (`/health`)

#### Health Check
- **GET** `/health`
- **Description:** Check application health status
- **Response:**
  ```json
  {
    "status": "UP",
    "timestamp": "datetime",
    "service": "Rentily Backend"
  }
  ```

---

## Data Schemas

### User Model
```json
{
  "id": "string",
  "name": "string",
  "email": "string",
  "password": "string (hashed)",
  "role": "string",
  "phoneNumber": "string",
  "expoPushToken": "string"
}
```

### Product Model
```json
{
  "id": "string",
  "title": "string",
  "description": "string",
  "price": 0.0,
  "category": "string",
  "type": "string (SELL or RENT)",
  "userId": "string",
  "status": "string (AVAILABLE, SOLD, RENTED)",
  "imageUrls": ["string"],
  "createdAt": "datetime"
}
```

### Transaction Model
```json
{
  "id": "string",
  "productId": "string",
  "sellerId": "string",
  "buyerId": "string",
  "type": "string (SELL, RENT, BUY)",
  "timestamp": "datetime",
  "status": "string (PENDING, COMPLETED, CANCELLED)"
}
```

### Review Model
```json
{
  "id": "string",
  "reviewerId": "string",
  "sellerId": "string",
  "productId": "string",
  "rating": 1-5,
  "comment": "string",
  "createdAt": "datetime"
}
```

### ChatRoom Model
```json
{
  "id": "string",
  "productId": "string",
  "sellerId": "string",
  "buyerId": "string"
}
```

### ChatMessage Model
```json
{
  "id": "string",
  "chatRoomId": "string",
  "senderId": "string",
  "receiverId": "string",
  "content": "string",
  "timestamp": "datetime"
}
```

### Cart Model
```json
{
  "id": "string",
  "userId": "string",
  "productId": ["string"]
}
```

### DTOs (Data Transfer Objects)

#### UserDTO
Same structure as User model (used for registration/updates)

#### ProductDTO
Same structure as Product model (used for create/update requests)

#### LoginDTO
```json
{
  "email": "string",
  "password": "string"
}
```

#### RestPasswordDTO
```json
{
  "email": "string",
  "newPassword": "string"
}
```

#### TransactionDTO
Same structure as Transaction model

#### ReviewDTO
```json
{
  "sellerId": "string",
  "productId": "string",
  "rating": 1-5,
  "comment": "string"
}
```

#### ChatMessageDTO
Same structure as ChatMessage model

#### ChatRoomDTO
```json
{
  "productId": "string",
  "sellerId": "string",
  "buyerId": "string"
}
```

#### CartDTO
```json
{
  "userId": "string",
  "products": [ProductDTO]
}
```

#### NotificationRequestDTO
```json
{
  "to": "string",
  "title": "string",
  "body": "string"
}
```

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