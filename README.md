# Library Management System

This is a Library Management System implemented using the Spring Boot framework. The system is designed to catalog books and manage borrowers efficiently.

## Features

- **Book Management:** Allows librarians to add, update, and delete books from the library catalog.
- **Borrower Management:** Enables librarians to manage borrower information including registration, updates, and deletion.
- **Checkout and Return:** Facilitates the process of borrowing and returning books, with due date tracking.
- **Security:** Implements role-based access control for administrators.

## Technologies Used

- **Spring Boot:** Provides the foundation for the application, simplifying configuration and deployment.
- **Spring Security:** Handles authentication and authorization of users.
- **Spring Data JPA:** Facilitates database interactions using the Java Persistence API.
- **H2 Database:** Used as the backend database to store library data.
- **Swagger:** Provides a user-friendly interface for API documentation and testing.
- **OAuth2 Resource Server:** Enables secure access to resources using spring's OAuth2 authentication and JWT for authorization.

## Getting Started

To run the Library Management System locally, follow these steps:

1. **Clone the repository:**
git clone https://github.com/JEN-dotcom/library-management-system.git

2. **Configure the database:**
- Set up a MySQL database and configure the connection properties in `application.yml`.

3. **Build and run the application:**
cd library-management-system
./mvnw spring-boot:run

4. **Access the application:**
Open a web browser and go to `http://localhost:8080/swagger-ui/index.html` to see the list of apis and functionalities available.

## Usage

- **Login:** Use the provided credentials to log in as an administrator.
- **Manage Books:** Add, update, or delete books from the catalog.
- **Manage Borrowers:** Register new borrowers, update their information, or deactivate accounts.

## Contributing

Contributions to the Library Management System are welcome! If you find any bugs or have suggestions for improvements, please open an issue or submit a pull request on GitHub.

## License

This project is licensed under the [MIT License](LICENSE).

## Authors

- [John Enyinwa](https://github.com/JEN-dotcom)
