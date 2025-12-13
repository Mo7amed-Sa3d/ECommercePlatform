# E-Commerce Platform

A Backend e-commerce platform built with **Spring Boot** (backend) and **MySQL** as the primary database. The system includes authentication, role based access, product management, cart & wishlist handling, order processing, payment/shipping integration, and a clean modular architecture.

---

## Vote for this [project](https://roadmap.sh/projects/ecommerce-api) on roadmap.sh

## 🚀 Features

### **Backend (Spring Boot)**
- User authentication (Basic Auth / JWT depending on configuration)
- Role-based authorization (ADMIN / USER)
- Product CRUD with categories, brands, images
- Cart & Wishlist management
- Order creation, order items, shipping address
- Stripe payment integration (optional)
- Shipping integration (FedEx / DHL / Aramex ready structure)
- Global exception handling
- DTO-based clean API responses
- Pagination, filtering, and sorting
- Entity relationships modeled via JPA/Hibernate
- Soft-delete support for certain entities

---

## 🏗 Project Structure

### **Backend**
```
backend/
├── src/main/java/com/ecommerce/ecommerceplatform
│   ├── configuration/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── exception/
│   ├── repository/
│   ├── security/
│   ├── service/
│   ├── utility/
│   └── EcommercePlatformApplication.java
└── src/main/resources/
    ├── application.properties
    └── Database/
```

## 🛢 Database Schema
Main entities:
- **User** & **Seller**
- **Product**
- **Category**
- **Brand** & **BrandImage**
- **ProductImage**
- **Cart** & **CartItem**
- **Wishlist** & **WishlistItem**
- **Order** & **OrderItem**
- **Address**
- **Payment**
- **Shipment**


Utility tables:
- **BlacklistedJWTToken** (optional — for JWT logout)

## 🛢 Database Conceptual Schema (ERD)
![ERD](src/main/resources/Database/ECommerce.drawio.svg)

## 🛢 Database Logical Schema (Relational Mapping)
- **Check the responsive diagram [here](https://dbdiagram.io/d/ECommerce-Relational-Mapping-6930bac7d6676488ba7e438e)**
<br>
<br>
<img src="src/main/resources/Database/ECommerce-Relational-Mapping.svg" style="background-color: white; padding: 2px" alt="https://dbdiagram.io/d/ECommerce-Relational-Mapping-6930bac7d6676488ba7e438e" /> 

[//]: # (![ERD]&#40;src/main/resources/Database/ECommerce-Relational-Mapping.svg&#41;)

## 💲 Payment Gateway Integration (Stripe Gateway)

The platform uses **Stripe** to handle secure payments. Payments are processed via **PaymentIntents**, which ensure that the correct amount is charged and provide support for multiple payment methods. The flow is:

1. Backend creates a PaymentIntent for the order.
2. Frontend confirms the payment using Stripe.js.
3. Stripe calls a webhook to notify the backend about payment status.
4. Backend updates order status in the database.
5. A payout service runs in the background to transfer Sellers' Dues everyday at 02:00 AM automatically

- For local testing use Stripe CLI
  - Use the following commands to get the webhook secret
```CLI
stripe login
stripe listen --forward-to localhost:8080/api/stripe-webhook
```
- Update Your API Keys in `application.properties` file
```properties
stripe.secret.key=sk_test_51SacfWA..........
stripe.webhook.secret=whsec_9414ea..........
```
## 🚀 Caching (Redis Integration)

The platform uses **Redis** as a high-performance, in-memory caching layer to improve application speed and reduce database load.

### **Why Redis?**

Redis provides:

- ⚡ Ultra-fast read/write operations
- 🧠 In-memory key–value storage
- 🗄️ Automatic expiration (TTL) support
- 🔒 Secure session/token handling
- 🌐 Distributed and scalable architecture

This makes it ideal for caching frequently accessed data such as products, categories, and user-related information.

---

### **How Caching Works**

1. **Cache Lookup**  
   The system checks Redis for cached data using a dedicated key (e.g., `product:{id}`).

2. **Cache Hit**  
   If the value exists in Redis, it is returned immediately without querying the database.

3. **Cache Miss**  
   If not found:
  - The data is retrieved from the database.
  - The result is stored in Redis with a TTL (expiration time).
  - Future requests are served from the cached value.

---

### **Run Redis**
* Using Docker
```bash
docker run -p 6379:6379 --name redis -d redis 
```
* For other hosting platforms and more info see the [Redis documentation](https://redis.io/docs/latest/develop/)
### **Caching Example**

```java
import com.ecommerce.ecommerceplatform.configuration.cache.CacheNames;

@Cacheable(value = CacheNames.products, key = "#id")
public Product getProduct(Long id) {
    /// Database call - Cache Miss
}
```
# API Documentation

Base URL: `http://localhost:8080`

<details>
<summary><b>Authentication</b></summary>

### Register User
**POST** `/api/auth/register`

Register a new user account.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "securepassword123",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890",
  "role": "USER"
}
```

**Response:**
```json
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890",
  "createdAt": "2024-05-14T10:30:00Z",
  "lastLogin": "2024-05-14T10:30:00Z",
  "role": "USER"
}
```

### Register Seller
**POST** `/api/auth/registerSeller`

Register a new seller account.

**Request Body:**
```json
{
  "email": "seller@example.com",
  "password": "securepassword123",
  "firstName": "Jane",
  "lastName": "Smith",
  "phone": "+1234567891",
  "role": "SELLER",
  "userId": 2,
  "sellerName": "Jane's Store",
  "verified": false,
  "createdAt": "2024-05-14T10:30:00Z"
}
```

**Response:**
```json
{
  "id": 2,
  "email": "seller@example.com",
  "firstName": "Jane",
  "lastName": "Smith",
  "phone": "+1234567891",
  "createdAt": "2024-05-14T10:30:00Z",
  "lastLogin": "2024-05-14T10:30:00Z",
  "role": "SELLER"
}
```

### Register Admin
**POST** `/api/auth/registerAdmin`

Register a new admin account.

**Request Body:**
```json
{
  "email": "admin@example.com",
  "password": "securepassword123",
  "firstName": "Admin",
  "lastName": "User",
  "phone": "+1234567892",
  "role": "ADMIN"
}
```

**Response:**
```json
{
  "id": 3,
  "email": "admin@example.com",
  "firstName": "Admin",
  "lastName": "User",
  "phone": "+1234567892",
  "createdAt": "2024-05-14T10:30:00Z",
  "lastLogin": "2024-05-14T10:30:00Z",
  "role": "ADMIN"
}
```

### Login
**POST** `/api/auth/login`

Authenticate user and get access token.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "securepassword123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Logout
**POST** `/api/auth/logout`

Logout user session.

**Headers:**
- `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`

**Response:**
```
"Logged out successfully"
```
</details>

<details>
<summary><b>Products</b></summary>

### Get All Products
**GET** `/api/products`

Retrieve all products.

**Response:**
```json
[
  {
    "id": 1,
    "sku": "PROD001",
    "title": "Smartphone X",
    "description": "Latest smartphone with advanced features",
    "basePrice": 999.99,
    "active": true,
    "attributes": {
      "color": "black",
      "storage": "128GB"
    },
    "createdAt": "2024-05-14T10:30:00Z",
    "images": [
      {
        "id": 1,
        "url": "https://example.com/image1.jpg",
        "altText": "Smartphone X front view",
        "displayOrder": 1
      }
    ]
  }
]
```

### Create Product
**POST** `/api/products`

Create a new product.

**Request Body:**
```json
{
  "sku": "PROD002",
  "title": "Laptop Pro",
  "description": "High-performance laptop",
  "basePrice": 1499.99,
  "active": true,
  "attributes": {
    "processor": "Intel i7",
    "ram": "16GB",
    "storage": "512GB SSD"
  },
  "createdAt": "2024-05-14T10:30:00Z",
  "brandId": 1,
  "categoryId": 2
}
```

**Response:**
```json
{
  "id": 2,
  "sku": "PROD002",
  "title": "Laptop Pro",
  "description": "High-performance laptop",
  "basePrice": 1499.99,
  "active": true,
  "attributes": {
    "processor": "Intel i7",
    "ram": "16GB",
    "storage": "512GB SSD"
  },
  "createdAt": "2024-05-14T10:30:00Z",
  "images": []
}
```

### Get Product by ID
**GET** `/api/products/{productId}`

Retrieve a specific product by ID.

**Path Parameters:**
- `productId` (integer): Product ID

**Response:**
```json
{
  "id": 1,
  "sku": "PROD001",
  "title": "Smartphone X",
  "description": "Latest smartphone with advanced features",
  "basePrice": 999.99,
  "active": true,
  "attributes": {
    "color": "black",
    "storage": "128GB"
  },
  "createdAt": "2024-05-14T10:30:00Z",
  "images": [
    {
      "id": 1,
      "url": "https://example.com/image1.jpg",
      "altText": "Smartphone X front view",
      "displayOrder": 1
    }
  ]
}
```

### Update Product
**PUT** `/api/products/{productId}`

Update an existing product.

**Path Parameters:**
- `productId` (integer): Product ID

**Request Body:**
```json
{
  "sku": "PROD001-UPDATED",
  "title": "Smartphone X Pro",
  "description": "Updated description",
  "basePrice": 1099.99,
  "active": true,
  "attributes": {
    "color": "black",
    "storage": "256GB"
  },
  "createdAt": "2024-05-14T10:30:00Z",
  "brandId": 1,
  "categoryId": 1
}
```

**Response:**
```json
{
  "id": 1,
  "sku": "PROD001-UPDATED",
  "title": "Smartphone X Pro",
  "description": "Updated description",
  "basePrice": 1099.99,
  "active": true,
  "attributes": {
    "color": "black",
    "storage": "256GB"
  },
  "createdAt": "2024-05-14T10:30:00Z",
  "images": [
    {
      "id": 1,
      "url": "https://example.com/image1.jpg",
      "altText": "Smartphone X front view",
      "displayOrder": 1
    }
  ]
}
```

### Delete Product
**DELETE** `/api/products/{productId}`

Delete a product.

**Path Parameters:**
- `productId` (integer): Product ID

**Response:**
```
"Product deleted successfully"
```

### Get Products by Category
**GET** `/api/products/category/{categoryId}`

Retrieve products by category ID.

**Path Parameters:**
- `categoryId` (integer): Category ID

**Response:**
```json
[
  {
    "id": 1,
    "sku": "PROD001",
    "title": "Smartphone X",
    "description": "Latest smartphone with advanced features",
    "basePrice": 999.99,
    "active": true,
    "attributes": {
      "color": "black",
      "storage": "128GB"
    },
    "createdAt": "2024-05-14T10:30:00Z",
    "images": [
      {
        "id": 1,
        "url": "https://example.com/image1.jpg",
        "altText": "Smartphone X front view",
        "displayOrder": 1
      }
    ]
  }
]
```

### Upload Product Images
**POST** `/api/products/{productId}/images`

Upload images for a product.

**Path Parameters:**
- `productId` (integer): Product ID

**Query Parameters:**
- `images` (array of files): Image files to upload

**Response:**
```json
[
  "https://example.com/image1.jpg",
  "https://example.com/image2.jpg"
]
```

### Delete Product Image
**DELETE** `/api/products/{productId}/images/{imageId}`

Delete a product image.

**Path Parameters:**
- `productId` (integer): Product ID
- `imageId` (integer): Image ID

**Response:**
```
"Image deleted successfully"
```
</details>

<details>
<summary><b>Categories</b></summary>

### Get All Categories
**GET** `/api/categories`

Retrieve all categories.

**Response:**
```json
[
  {
    "id": 1,
    "name": "Electronics",
    "parentId": null
  },
  {
    "id": 2,
    "name": "Smartphones",
    "parentId": 1
  }
]
```

### Create Category
**POST** `/api/categories`

Create a new category.

**Request Body:**
```json
{
  "name": "Laptops",
  "parentId": 1
}
```

**Response:**
```json
{
  "id": 3,
  "name": "Laptops",
  "parentId": 1
}
```

### Get Products by Category
**GET** `/api/categories/{categoryId}/products`

Retrieve products by category ID.

**Path Parameters:**
- `categoryId` (integer): Category ID

**Response:**
```json
[
  {
    "id": 1,
    "sku": "PROD001",
    "title": "Smartphone X",
    "description": "Latest smartphone with advanced features",
    "basePrice": 999.99,
    "active": true,
    "attributes": {
      "color": "black",
      "storage": "128GB"
    },
    "createdAt": "2024-05-14T10:30:00Z",
    "images": [
      {
        "id": 1,
        "url": "https://example.com/image1.jpg",
        "altText": "Smartphone X front view",
        "displayOrder": 1
      }
    ]
  }
]
```

### Delete Category
**DELETE** `/api/categories/{categoryId}`

Delete a category.

**Path Parameters:**
- `categoryId` (integer): Category ID

**Response:**
```
"Category deleted successfully"
```
</details>

<details>
<summary><b>Brands</b></summary>

### Get All Brands
**GET** `/api/brand`

Retrieve all brands.

**Response:**
```json
[
  {
    "id": 1,
    "name": "Apple",
    "description": "Technology company",
    "country": "USA",
    "createdAt": "2024-05-14T10:30:00Z",
    "imageUrl": "https://example.com/apple-logo.jpg"
  }
]
```

### Create Brand
**POST** `/api/brand`

Create a new brand.

**Request Body:**
```json
{
  "name": "Samsung",
  "description": "Electronics manufacturer",
  "country": "South Korea",
  "createdAt": "2024-05-14T10:30:00Z"
}
```

**Response:**
```json
{
  "id": 2,
  "name": "Samsung",
  "description": "Electronics manufacturer",
  "country": "South Korea",
  "createdAt": "2024-05-14T10:30:00Z",
  "imageUrl": null
}
```

### Get Brand by ID
**GET** `/api/brand/{brandId}`

Retrieve a specific brand by ID.

**Path Parameters:**
- `brandId` (integer): Brand ID

**Response:**
```json
{
  "id": 1,
  "name": "Apple",
  "description": "Technology company",
  "country": "USA",
  "createdAt": "2024-05-14T10:30:00Z",
  "imageUrl": "https://example.com/apple-logo.jpg"
}
```

### Upload Brand Image
**POST** `/api/brand/image/{brandId}`

Upload an image for a brand.

**Path Parameters:**
- `brandId` (integer): Brand ID

**Request Body:**
```json
{
  "image": "base64-encoded-image-data"
}
```

**Response:**
```
"https://example.com/brand-image.jpg"
```
</details>

<details>
<summary><b>Cart</b></summary>

### Get Cart
**GET** `/api/users/cart`

Retrieve the user's cart.

**Response:**
```json
{
  "id": 1,
  "updatedAt": "2024-05-14T10:30:00Z",
  "userID": 1,
  "cartItemResponseDTOList": [
    {
      "id": 1,
      "cartId": 1,
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

### Add Item to Cart
**POST** `/api/users/cart`

Add an item to the cart.

**Request Body:**
```json
{
  "productId": 1,
  "quantity": 1
}
```

**Response:**
```json
{
  "id": 1,
  "updatedAt": "2024-05-14T10:30:00Z",
  "userID": 1,
  "cartItemResponseDTOList": [
    {
      "id": 1,
      "cartId": 1,
      "productId": 1,
      "quantity": 3
    }
  ]
}
```

### Remove Item from Cart
**DELETE** `/api/users/cart/{itemId}`

Remove an item from the cart.

**Path Parameters:**
- `itemId` (integer): Cart item ID

**Response:**
```json
{
  "id": 1,
  "updatedAt": "2024-05-14T10:30:00Z",
  "userID": 1,
  "cartItemResponseDTOList": []
}
```
</details>

<details>
<summary><b>Wishlist</b></summary>

### Get Wishlist
**GET** `/api/users/wishlist`

Retrieve the user's wishlist.

**Response:**
```json
{
  "wishlistItems": [
    {
      "productId": 1,
      "variantId": 1
    }
  ]
}
```

### Add to Wishlist
**POST** `/api/users/wishlist`

Add an item to the wishlist.

**Request Body:**
```json
{
  "productId": 2
}
```

**Response:**
```json
{
  "wishlistItems": [
    {
      "productId": 1,
      "variantId": 1
    },
    {
      "productId": 2,
      "variantId": null
    }
  ]
}
```

### Remove from Wishlist
**DELETE** `/api/users/wishlist/{itemId}`

Remove an item from the wishlist.

**Path Parameters:**
- `itemId` (integer): Wishlist item ID

**Response:**
```
"Item removed from wishlist"
```
</details>

<details>
<summary><b>Orders</b></summary>

### Get Orders
**GET** `/api/users/orders`

Retrieve user's orders.

**Response:**
```json
[
  {
    "id": 1,
    "status": "PROCESSING",
    "totalAmount": 1999.98,
    "currency": "USD",
    "createdAt": "2024-05-14T10:30:00Z",
    "orderItemResponseDTOList": [
      {
        "id": 1,
        "quantity": 2,
        "unitPrice": 999.99,
        "taxAmount": 159.99
      }
    ],
    "shipmentId": 123
  }
]
```

### Checkout
**POST** `/api/users/orders/checkout`

Checkout cart and create an order.

**Request Body:**
```json
123
```
*Note: The request body is just the cart ID as an integer.*

**Response:**
```json
{
  "orderId": 1,
  "orderItemList": [
    {
      "id": 1,
      "quantity": 2,
      "unitPrice": 999.99,
      "taxAmount": 159.99
    }
  ],
  "finalTotal": 2159.97,
  "shipmentId": 123
}
```
</details>

<details>
<summary><b>User Management</b></summary>

### Get Addresses
**GET** `/api/users/addresses`

Retrieve user's addresses.

**Response:**
```json
[
  {
    "id": 1,
    "fullName": "John Doe",
    "line1": "123 Main St",
    "line2": "Apt 4B",
    "city": "New York",
    "region": "NY",
    "postalCode": "10001",
    "country": "USA",
    "phone": "+1234567890",
    "longitude": -74.006,
    "latitude": 40.7128
  }
]
```

### Add Address
**POST** `/api/users/addresses`

Add a new address.

**Request Body:**
```json
{
  "fullName": "Jane Smith",
  "line1": "456 Oak Ave",
  "line2": "",
  "city": "Los Angeles",
  "region": "CA",
  "postalCode": "90001",
  "country": "USA",
  "phone": "+1234567891",
  "longitude": -118.2437,
  "latitude": 34.0522
}
```

**Response:**
```json
{
  "id": 2,
  "fullName": "Jane Smith",
  "line1": "456 Oak Ave",
  "line2": "",
  "city": "Los Angeles",
  "region": "CA",
  "postalCode": "90001",
  "country": "USA",
  "phone": "+1234567891",
  "longitude": -118.2437,
  "latitude": 34.0522
}
```

### Delete Address
**DELETE** `/api/users/addresses/{addressId}`

Delete an address.

**Path Parameters:**
- `addressId` (integer): Address ID

**Response:**
```
"Address deleted successfully"
```

### Get Seller Onboarding Link
**GET** `/api/users/{sellerId}/onboarding-link`

Get Stripe onboarding link for seller.

**Path Parameters:**
- `sellerId` (integer): Seller ID

**Response:**
```json
{
  "url": "https://connect.stripe.com/setup/e/acct_123..."
}
```
</details>

<details>
<summary><b>Payments</b></summary>

### Create Payment Intent
**POST** `/api/payments/create-payment-intent`

Create a Stripe payment intent.

**Request Body:**
```json
{
  "amount": 215997,
  "orderId": 1,
  "currency": "usd"
}
```

**Response:**
```json
{
  "clientSecret": "pi_3Nt6z9LkdIwHu7ix0pJ...",
  "id": "pi_3Nt6z9LkdIwHu7ix0pJ..."
}
```

### Get Onboarding Link
**GET** `/api/payments/onboarding-link`

Get Stripe onboarding link.

**Response:**
```json
{
  "url": "https://connect.stripe.com/setup/e/acct_123..."
}
```

### Get Account Requirements
**GET** `/api/payments/acount-requirement`

Get Stripe account requirements.

**Response:**
```json
[
  "individual.first_name",
  "individual.last_name",
  "individual.email"
]
```
</details>

<details>
<summary><b>Other</b></summary>

### Send Email
**POST** `/api/mail/send`

Send an email.

**Request Body:**
```json
{
  "to": "recipient@example.com",
  "subject": "Welcome to Our Store",
  "text": "Thank you for registering!",
  "isHtml": false
}
```

**Response:**
```
"Email sent successfully"
```

### Stripe Webhook
**POST** `/api/stripe-webhook`

Handle Stripe webhook events.

**Request Body:**
```json
{
  "id": "evt_1Nt7...",
  "object": "event",
  "api_version": "2023-10-16",
  "created": 1699876543,
  "livemode": false,
  "pending_webhooks": 1,
  "type": "payment_intent.succeeded"
}
```

**Response:**
```
"Webhook received"
```
</details>

## 🔧 Installation & Setup


### **Prerequisites**
- Docker


### **Steps**
1. Clone the project


4. Build and start the docker images:
```bash
docker compose up --build
```

## 💬 Contact
**Developer:** Mohamed Saad Abdel Ghaffar

