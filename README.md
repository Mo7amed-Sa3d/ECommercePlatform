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
@Cacheable(value = "product", key = "#id")
public Product getProduct(Long id) {
    /// Database call
}
```

# E-Commerce Platform API Documentation

Base URL: `http://localhost:8080`

---

<details>
<summary><strong>Product APIs</strong></summary>

### Get All Products
**GET** `/api/products`  
**Response:** `200 OK`  
Returns list of products.

**Response Fields (ProductResponseDTO):**
- `id` (integer)
- `sku` (string)
- `title` (string)
- `description` (string)
- `basePrice` (number)
- `active` (boolean)
- `attributes` (object)
- `createdAt` (string, date-time)
- `images` (array of ProductImageResponseDTO)
    - `id` (integer)
    - `url` (string)
    - `altText` (string)
    - `displayOrder` (integer)

### Get Product by ID
**GET** `/api/products/{productId}`  
**Parameters:**
- `productId` (path, integer, required)

**Response:** `200 OK` (ProductResponseDTO)

### Create Product
**POST** `/api/products`  
**Request Body (ProductRequestDTO):**
- `sku` (string)
- `title` (string)
- `description` (string)
- `basePrice` (number)
- `active` (boolean)
- `attributes` (object)
- `createdAt` (string, date-time)
- `brandId` (integer)
- `categoryId` (integer)

**Response:** `200 OK` (ProductResponseDTO)

### Update Product
**PUT** `/api/products/{productId}`  
**Parameters:** `productId` (integer, required)  
**Request Body:** `ProductRequestDTO`  
**Response:** `200 OK` (ProductResponseDTO)

### Delete Product
**DELETE** `/api/products/{productId}`  
**Parameters:** `productId` (integer, required)  
**Response:** `200 OK` (string)

### Upload Product Images
**POST** `/api/products/{productId}/images`  
**Parameters:**
- `productId` (path, integer, required)
- `images` (query, array of binary files, required)  
  **Response:** `200 OK` (array of string URLs)

### Delete Product Image
**DELETE** `/api/products/{productId}/images/{imageId}`  
**Parameters:**
- `productId` (integer, required)
- `imageId` (integer, required)  
  **Response:** `200 OK` (string)

### Get Products by Category
**GET** `/api/products/category/{categoryId}`  
**Parameters:** `categoryId` (integer, required)  
**Response:** `200 OK` (array of ProductResponseDTO)

</details>

---

<details>
<summary><strong>User APIs</strong></summary>

### Wishlist

#### Get Wishlist
**GET** `/api/users/wishlist`  
**Response:** `200 OK` (WishlistResponseDTO)
- `wishlistItems` (array of WishlistItemResponseDTO)
    - `productId` (integer)
    - `variantId` (integer)

#### Add Item to Wishlist
**POST** `/api/users/wishlist`  
**Request Body (WishlistItemRequestDTO):**
- `productId` (integer)  
  **Response:** `200 OK` (WishlistResponseDTO)

#### Delete Item from Wishlist
**DELETE** `/api/users/wishlist/{itemId}`  
**Parameters:** `itemId` (integer, required)  
**Response:** `200 OK` (string)

### Cart

#### Get Cart
**GET** `/api/users/cart`  
**Response:** `200 OK` (CartResponseDTO)
- `id` (integer)
- `updatedAt` (string, date-time)
- `userID` (integer)
- `cartItemResponseDTOList` (array of CartItemResponseDTO)
    - `id` (integer)
    - `cartId` (integer)
    - `productId` (integer)
    - `quantity` (integer)

#### Add Item to Cart
**POST** `/api/users/cart`  
**Request Body (CartItemRequestDTO):**
- `productId` (integer)
- `quantity` (integer)  
  **Response:** `200 OK` (CartResponseDTO)

#### Remove Item from Cart
**DELETE** `/api/users/cart/{itemId}`  
**Parameters:** `itemId` (integer, required)  
**Response:** `200 OK` (CartResponseDTO)

### Addresses

#### Get Addresses
**GET** `/api/users/addresses`  
**Response:** `200 OK` (array of AddressResponseDTO)
- `id` (integer)
- `fullName` (string)
- `line1` (string)
- `line2` (string)
- `city` (string)
- `region` (string)
- `postalCode` (string)
- `country` (string)
- `phone` (string)
- `longitude` (double)
- `latitude` (double)

#### Add Address
**POST** `/api/users/addresses`  
**Request Body (AddressRequestDTO):**
- `fullName` (string)
- `line1` (string)
- `line2` (string)
- `city` (string)
- `region` (string)
- `postalCode` (string)
- `country` (string)
- `phone` (string)
- `longitude` (double)
- `latitude` (double)  
  **Response:** `200 OK` (AddressResponseDTO)

#### Delete Address
**DELETE** `/api/users/addresses/{addressId}`  
**Parameters:** `addressId` (integer, required)  
**Response:** `200 OK` (string)

### Onboarding Link
**GET** `/api/users/{sellerId}/onboarding-link`  
**Parameters:** `sellerId` (integer, required)  
**Response:** `200 OK` (object)

</details>

---

<details>
<summary><strong>Authentication APIs</strong></summary>

### Register User
**POST** `/api/auth/register`  
**Request Body (UserRequestDTO):**
- `email` (string)
- `password` (string)
- `firstName` (string)
- `lastName` (string)
- `phone` (string)
- `role` (string)  
  **Response:** `200 OK` (UserResponseDTO)
- `id` (integer)
- `email` (string)
- `firstName` (string)
- `lastName` (string)
- `phone` (string)
- `createdAt` (string, date-time)
- `lastLogin` (string, date-time)
- `role` (string)

### Register Seller
**POST** `/api/auth/registerSeller`  
**Request Body (SellerRequestDTO):**
- `email`, `password`, `firstName`, `lastName`, `phone`, `role` (string)
- `userId` (integer)
- `sellerName` (string)
- `verified` (boolean)
- `createdAt` (string, date-time)  
  **Response:** `200 OK` (UserResponseDTO)

### Register Admin
**POST** `/api/auth/registerAdmin`  
**Request Body:** `UserRequestDTO`  
**Response:** `200 OK` (UserResponseDTO)

### Login
**POST** `/api/auth/login`  
**Request Body (AuthRequestDto):**
- `email` (string)
- `password` (string)  
  **Response:** `200 OK` (AuthResponseDto)
- `token` (string)

### Logout
**POST** `/api/auth/logout`  
**Headers:** `Authorization` (string, required)  
**Response:** `200 OK` (string)

</details>

---

<details>
<summary><strong>Order & Payment APIs</strong></summary>

### Checkout
**POST** `/api/users/orders/checkout`  
**Request Body:** `integer` (orderId)  
**Response:** `200 OK` (OrderSummaryDTO)
- `orderId` (integer)
- `orderItemList` (array of OrderItemResponseDTO)
    - `id` (integer)
    - `quantity` (integer)
    - `unitPrice` (number)
    - `taxAmount` (number)
- `finalTotal` (number)
- `shipmentId` (integer)

### Get Orders
**GET** `/api/users/orders`  
**Response:** `200 OK` (array of OrderResponseDTO)
- `id`, `status`, `totalAmount`, `currency`, `createdAt`, `shipmentId`
- `orderItemResponseDTOList` same as above

### Create Payment Intent
**POST** `/api/payments/create-payment-intent`  
**Request Body (PaymentRequest):**
- `amount` (integer)
- `orderId` (integer)
- `currency` (string)  
  **Response:** `200 OK` (object)

### Stripe Webhook
**POST** `/api/stripe-webhook`  
**Request Body:** `string`  
**Response:** `200 OK` (string)

### Onboarding Link
**GET** `/api/payments/onboarding-link`  
**Response:** `200 OK` (object)

### Account Requirements
**GET** `/api/payments/acount-requirement`  
**Response:** `200 OK` (array of strings)

</details>

---

<details>
<summary><strong>Mailing API</strong></summary>

### Send Mail
**POST** `/api/mail/send`  
**Request Body (MailRequestDTO):**
- `to` (string)
- `subject` (string)
- `text` (string)
- `isHtml` (boolean)  
  **Response:** `200 OK` (string)

</details>

---

<details>
<summary><strong>Category APIs</strong></summary>

### Get All Categories
**GET** `/api/categories`  
**Response:** `200 OK` (array of CategoryResponseDTO)
- `id` (integer)
- `name` (string)
- `parentId` (integer)

### Create Category
**POST** `/api/categories`  
**Request Body (CategoryRequestDTO):**
- `name` (string)
- `parentId` (integer)  
  **Response:** `200 OK` (CategoryResponseDTO)

### Get Products by Category
**GET** `/api/categories/{categoryId}/products`  
**Parameters:** `categoryId` (integer, required)  
**Response:** `200 OK` (array of ProductResponseDTO)

### Delete Category
**DELETE** `/api/categories/{categoryId}`  
**Parameters:** `categoryId` (integer, required)  
**Response:** `200 OK` (string)

</details>

---

<details>
<summary><strong>Brand APIs</strong></summary>

### Get All Brands
**GET** `/api/brand`  
**Response:** `200 OK` (array of BrandResponseDTO)
- `id`, `name`, `description`, `country`, `createdAt`, `imageUrl`

### Add Brand
**POST** `/api/brand`  
**Request Body (BrandRequestDTO):**
- `name`, `description`, `country` (string)
- `createdAt` (string, date-time)  
  **Response:** `200 OK` (BrandResponseDTO)

### Get Brand by ID
**GET** `/api/brand/{brandId}`  
**Parameters:** `brandId` (integer, required)  
**Response:** `200 OK` (BrandResponseDTO)

### Upload Brand Image
**POST** `/api/brand/image/{brandId}`  
**Parameters:** `brandId` (integer, required)  
**Request Body:** `{ image: file }`  
**Response:** `200 OK` (string)

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

