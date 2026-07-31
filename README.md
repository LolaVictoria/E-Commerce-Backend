# E-Commerce-Backend - Alaba Commerce API

A secure RESTful e-commerce backend built with **Spring Boot**.  
It provides authentication, product management, and shopping cart functionality using JWT authentication.

---

## 🚀 Features

### Authentication
- User registration
- User login
- JWT Authentication
- Password encryption with BCrypt

### Products
- Create product
- Update product
- Delete product
- Get all products
- Get product by ID
- Get seller's products
- Search products
- Filter by category
- Pagination & sorting

### Cart
- Add item to cart
- View cart
- Update cart quantity
- Remove cart item
- Clear cart

### Documentation
- Swagger UI

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- JWT
- Maven
- Swagger (OpenAPI)
---

## 🔐 Authentication

Protected endpoints require:

```
Authorization: Bearer <JWT_TOKEN>
```

Obtain the token from:

```
POST /auth/login
```

---

## 📌 API Endpoints

### Authentication

| Method | Endpoint |
|--------|----------|
| POST | `/auth/register` |
| POST | `/auth/login` |

### Products

| Method | Endpoint |
|--------|----------|
| POST | `/products` |
| GET | `/products` |
| GET | `/products/{id}` |
| PUT | `/products/{id}` |
| DELETE | `/products/{id}` |
| GET | `/products/my-products` |

### Cart

| Method | Endpoint |
|--------|----------|
| POST | `/cart/items` |
| GET | `/cart` |
| PUT | `/cart/items/{id}` |
| DELETE | `/cart/items/{id}` |
| DELETE | `/cart` |

---

## 📖 Swagger Documentation

After starting the application:

```
https://e-commerce-backend-production-2580.up.railway.app/swagger-ui/index.html
```
---

## ▶ Running the Project

Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/alaba-commerce.git
```

Navigate into the project

```bash
cd alaba-commerce
```

Run the application

```bash
mvn spring-boot:run
```

---

## 📬 Postman Collection

The complete Postman collection is included in:

postman/
└── Alaba-Commerce-API.postman_collection.json
```

---

## 📄 License

This project is for learning and portfolio purposes.
