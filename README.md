# 🛒 Alaba Commerce API

A secure, production-ready RESTful e-commerce backend built with **Spring Boot**.

The API powers the Alaba Commerce marketplace by providing authentication, seller management, product management, shopping cart functionality, and order processing using **JWT Authentication**.

---

# ✨ Features

## 🔐 Authentication

- User registration
- User login
- JWT Authentication
- Password encryption with BCrypt
- Stateless authentication
- Role-based authorization (USER & SELLER)

---

## 👤 Seller Profile

- Create seller profile
- View seller profile
- Update seller profile
- Delete seller profile
- Automatically upgrade user role from **USER** → **SELLER**
- Automatically issue a new JWT after role upgrade

---

## 📦 Products

- Create products
- Update products
- Delete products
- Get all products
- Get product by ID
- View seller's products
- Search products
- Filter products by category
- Pagination & sorting
- Product ownership validation
- Product image upload using **Cloudinary**

---

## 🛒 Shopping Cart

- Add items to cart
- View cart
- Update cart quantity
- Remove cart items
- Clear cart

---

## 📋 Orders

### Customer

- Checkout
- View purchase history
- View order details

### Seller

- View orders containing seller's products
- Update order status

---

## 📖 API Documentation

- Swagger UI (OpenAPI)

---

# 🛠 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- JWT
- Maven
- Swagger (OpenAPI)
- Cloudinary

---

# 🔐 Authentication

Protected endpoints require:

```http
Authorization: Bearer <JWT_TOKEN>
```

Obtain a token from:

```http
POST /auth/login
```

---

# 📌 API Endpoints

## Authentication

| Method | Endpoint |
|---------|----------|
| POST | `/auth/register` |
| POST | `/auth/login` |

---

## Seller Profile

| Method | Endpoint |
|---------|----------|
| POST | `/seller-profile` |
| GET | `/seller-profile/me` |
| PUT | `/seller-profile` |
| DELETE | `/seller-profile` |

---

## Products

| Method | Endpoint |
|---------|----------|
| POST | `/products` |
| GET | `/products` |
| GET | `/products/{id}` |
| PUT | `/products/{id}` |
| DELETE | `/products/{id}` |
| GET | `/products/my-products` |

---

## Shopping Cart

| Method | Endpoint |
|---------|----------|
| POST | `/cart/items` |
| GET | `/cart` |
| PUT | `/cart/items/{id}` |
| DELETE | `/cart/items/{id}` |
| DELETE | `/cart` |

---

## Orders

| Method | Endpoint |
|---------|----------|
| POST | `/orders/checkout` |
| GET | `/orders` |
| GET | `/orders/{id}` |
| GET | `/orders/seller` |
| PUT | `/orders/{id}/status` |

---

# 🌐 Live Website

The frontend application is available at:

🔗 **https://alaba-market.vercel.app**

---

# 📖 Swagger Documentation

Explore and test the API:

🔗 **https://e-commerce-backend-production-2580.up.railway.app/swagger-ui/index.html**

---

# ▶ Running the Project

Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/alaba-commerce.git
```

Navigate into the project

```bash
cd alaba-commerce
```

Install dependencies

```bash
mvn clean install
```

Run the application

```bash
mvn spring-boot:run
```

---

# ⚙️ Environment Variables

The following environment variables are required:

```properties
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=

JWT_SECRET=

CLOUDINARY_CLOUD_NAME=
CLOUDINARY_API_KEY=
CLOUDINARY_API_SECRET=
```

---

# 📬 Postman Collection

The Postman collection is included in:

```
postman/
└── Alaba-Commerce-API.postman_collection.json
```

---

# 📄 License

This project was built for learning, portfolio, and demonstration purposes.
