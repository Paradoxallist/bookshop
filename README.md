#  Online Book Store

> The Online Book Store is a Java Spring Boot application that offers a complete solution
> for managing an online bookstore. It enables users to explore
> a diverse range of books, create customized shopping carts, and securely buy items online.
> Additionally, administrators have access to manage inventory, process orders, and analyze sales data.

## Used Technologies
**Core Technologies:**
* Java 17
* Maven

**Spring Framework:**
* Spring Boot
* Spring Boot Web
* Spring Data JPA
* Spring Boot Security
* Spring Boot Validation

**Database:**
* MySQL
* Hibernate
* Liquibase

**Testing:**
* Spring Boot Starter Test
* JUnit
* Mockito
* Docker Test Containers

**Auxiliary Libraries and tools:**
* Docker
* Lombok
* MapStruct
* Swagger
* JWT

## Endpoints
**AuthController:** Handles registration and login requests, supporting both Basic and JWT authentication.
* `POST: /api/auth/registration` - The endpoint for registration.
* `POST: /api/auth/login` - The endpoint for login.

**BookController:** Handles requests for book CRUD operations.
* `GET: /api/books` - The endpoint for retrieving all books.
* `GET: /api/books/{id}` - The endpoint for searching a specific book by ID.
* `POST: /api/books` - The endpoint for creating new book. (Admin Only)
* `PUT: /api/books/{id}` - The endpoint for updating book information. (Admin Only)
* `DELETE: /api/books/{id}` - The endpoint for deleting book. (Admin Only)

**CategoryController:** Handles requests for category CRUD operations and retrieving all books by category.
* `GET: /api/categories` - The endpoint for retrieving all categories.
* `GET: /api/categories/{id}` - The endpoint for retrieving a specific category by its ID.
* `GET: /api/categories/{id}/books` - The endpoint for retrieving books by a category ID.
* `POST: /api/categories` - The endpoint for creating a new category. (Admin Only)
* `PUT: /api/categories/{id}` - The endpoint for updating category information. (Admin Only)
* `DELETE: /api/categories/{id}` - The endpoint for deleting categories. (Admin Only)

**OrderController:** Handles requests for order CRUD operations.
* `GET: /api/orders` - The endpoint for retrieving orders history.
* `GET: /api/orders/{order-id}/items` - The endpoint for retrieving order items from a specific order.
* `GET: /api/orders/{order-id}/items/{item-id}` - The endpoint for retrieving a specific item from a specific order.
* `POST: /api/orders` - The endpoint for placing an order.
* `PATCH: /api/orders/{id}` - The endpoint for updating an order status. (Admin Only)

**ShoppingCartController:** Handles requests for shopping cart CRUD operations.
* `GET: /api/cart` - The endpoint for retrieving all items from a shopping cart.
* `POST: /api/cart` - The endpoint for adding item to a shopping cart.
* `PUT: /api/cart/cart-items/{cartItemId}` - The endpoint for updating quantity of a specific item in shopping cart.
* `DELETE: /api/cart/cart-items/{cartItemId}` - The endpoint for deleting items from a shopping cart.


## Run Project On Your Machine
1. Download [Java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and [Docker](https://www.docker.com/products/docker-desktop/).
2. Clone the repository:
    - Open your terminal and paste: `git clone https://github.com/Paradoxallist/bookshop.git`
3. Create the .env file with the corresponding variables:
    - Example:
    - ![image](https://github.com/Paradoxallist/bookshop/assets/106034974/f7f7ad3c-e9d1-43ae-b30f-f5e5391e0f69)
4. Build the project:
    - Open your terminal and paste: `mvn clean package`
5. Use Docker Compose:
    - Open your terminal and paste: `docker compose build` and `docker compose up`