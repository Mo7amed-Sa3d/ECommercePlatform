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
Returns a list of all products.

### Get Product by ID
**GET** `/api/products/{productId}`  
**Parameters:**
- `productId` (path, required, integer)

**Response:** `200 OK`  
Returns product details.

### Create Product
**POST** `/api/products`  
**Request Body:** `ProductRequestDTO`  
**Response:** `200 OK`  
Returns created product.

### Update Product
**PUT** `/api/products/{productId}`  
**Parameters:**
- `productId` (path, required, integer)

**Request Body:** `ProductRequestDTO`  
**Response:** `200 OK`  
Returns updated product.

### Delete Product
**DELETE** `/api/products/{productId}`  
**Parameters:**
- `productId` (path, required, integer)

**Response:** `200 OK`  
Returns confirmation string.

### Upload Product Images
**POST** `/api/products/{productId}/images`  
**Parameters:**
- `productId` (path, required, integer)
- `images` (query, required, array of binary files)

**Response:** `200 OK`  
Returns uploaded image URLs.

### Delete Product Image
**DELETE** `/api/products/{productId}/images/{imageId}`  
**Parameters:**
- `productId` (path, required, integer)
- `imageId` (path, required, integer)

**Response:** `200 OK`  
Returns confirmation string.

### Get Products by Category
**GET** `/api/products/category/{categoryId}`  
**Parameters:**
- `categoryId` (path, required, integer)

**Response:** `200 OK`  
Returns products in the category.

</details>

---

<details>
<summary><strong>User APIs</strong></summary>

### Wishlist

#### Get Wishlist
**GET** `/api/users/wishlist`  
**Response:** `200 OK`  
Returns the user's wishlist.

#### Add Item to Wishlist
**POST** `/api/users/wishlist`  
**Request Body:** `WishlistItemRequestDTO`  
**Response:** `200 OK`  
Returns updated wishlist.

#### Delete Item from Wishlist
**DELETE** `/api/users/wishlist/{itemId}`  
**Parameters:**
- `itemId` (path, required, integer)

**Response:** `200 OK`  
Returns confirmation string.

### Cart

#### Get Cart
**GET** `/api/users/cart`  
**Response:** `200 OK`  
Returns the user's cart.

#### Add Item to Cart
**POST** `/api/users/cart`  
**Request Body:** `CartItemRequestDTO`  
**Response:** `200 OK`  
Returns updated cart.

#### Remove Item from Cart
**DELETE** `/api/users/cart/{itemId}`  
**Parameters:**
- `itemId` (path, required, integer)

**Response:** `200 OK`  
Returns updated cart.

### Addresses

#### Get Addresses
**GET** `/api/users/addresses`  
**Response:** `200 OK`  
Returns user's addresses.

#### Add Address
**POST** `/api/users/addresses`  
**Request Body:** `AddressRequestDTO`  
**Response:** `200 OK`  
Returns created address.

#### Delete Address
**DELETE** `/api/users/addresses/{addressId}`  
**Parameters:**
- `addressId` (path, required, integer)

**Response:** `200 OK`  
Returns confirmation string.

### Onboarding Link
**GET** `/api/users/{sellerId}/onboarding-link`  
**Parameters:**
- `sellerId` (path, required, integer)

**Response:** `200 OK`  
Returns onboarding link object.

</details>

---

<details>
<summary><strong>Authentication APIs</strong></summary>

### Register User
**POST** `/api/auth/register`  
**Request Body:** `UserRequestDTO`  
**Response:** `200 OK`  
Returns created user.

### Register Seller
**POST** `/api/auth/registerSeller`  
**Request Body:** `SellerRequestDTO`  
**Response:** `200 OK`  
Returns created seller.

### Register Admin
**POST** `/api/auth/registerAdmin`  
**Request Body:** `UserRequestDTO`  
**Response:** `200 OK`  
Returns created admin.

### Login
**POST** `/api/auth/login`  
**Request Body:** `AuthRequestDto`  
**Response:** `200 OK`  
Returns auth token.

### Logout
**POST** `/api/auth/logout`  
**Headers:**
- `Authorization` (required, string)

**Response:** `200 OK`  
Returns confirmation string.

</details>

---

<details>
<summary><strong>Order & Payment APIs</strong></summary>

### Checkout
**POST** `/api/users/orders/checkout`  
**Request Body:** `integer` (orderId)  
**Response:** `200 OK`  
Returns `OrderSummaryDTO`.

### Get Orders
**GET** `/api/users/orders`  
**Response:** `200 OK`  
Returns list of `OrderResponseDTO`.

### Create Payment Intent
**POST** `/api/payments/create-payment-intent`  
**Request Body:** `PaymentRequest`  
**Response:** `200 OK`  
Returns payment intent object.

### Stripe Webhook
**POST** `/api/stripe-webhook`  
**Request Body:** `string` (raw event payload)  
**Response:** `200 OK`  
Returns confirmation string.

### Onboarding Link (Payment)
**GET** `/api/payments/onboarding-link`  
**Response:** `200 OK`  
Returns onboarding link object.

### Account Requirements
**GET** `/api/payments/acount-requirement`  
**Response:** `200 OK`  
Returns array of strings.

</details>

---

<details>
<summary><strong>Mailing API</strong></summary>

### Send Mail
**POST** `/api/mail/send`  
**Request Body:** `MailRequestDTO`  
**Response:** `200 OK`  
Returns confirmation string.

</details>

---

<details>
<summary><strong>Category APIs</strong></summary>

### Get All Categories
**GET** `/api/categories`  
**Response:** `200 OK`  
Returns list of `CategoryResponseDTO`.

### Create Category
**POST** `/api/categories`  
**Request Body:** `CategoryRequestDTO`  
**Response:** `200 OK`  
Returns created category.

### Get Products by Category
**GET** `/api/categories/{categoryId}/products`  
**Parameters:**
- `categoryId` (path, required, integer)

**Response:** `200 OK`  
Returns products in the category.

### Delete Category
**DELETE** `/api/categories/{categoryId}`  
**Parameters:**
- `categoryId` (path, required, integer)

**Response:** `200 OK`  
Returns confirmation string.

</details>

---

<details>
<summary><strong>Brand APIs</strong></summary>

### Get All Brands
**GET** `/api/brand`  
**Response:** `200 OK`  
Returns list of `BrandResponseDTO`.

### Add Brand
**POST** `/api/brand`  
**Request Body:** `BrandRequestDTO`  
**Response:** `200 OK`  
Returns created brand.

### Get Brand by ID
**GET** `/api/brand/{brandId}`  
**Parameters:**
- `brandId` (path, required, integer)

**Response:** `200 OK`  
Returns `BrandResponseDTO`.

### Upload Brand Image
**POST** `/api/brand/image/{brandId}`  
**Parameters:**
- `brandId` (path, required, integer)  
  **Request Body:** `{ image: file }`  
  **Response:** `200 OK`  
  Returns confirmation string.

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

