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
   - Alternatively, you can use the embedded H2 server. Access the H2 console by navigating to [H2 Console](http://localhost:8080/h2-console) in your web browser. (The login page will appear, use the appropriate credentials if configured)

3. **Build and run the application:**
cd library-management-system
./mvnw spring-boot:run

4. **Access the application:**
Open a web browser and go to `http://localhost:8080/swagger-ui/index.html` to see the list of apis and functionalities available.

## Usage

- **Login:** To log in as an administrator, follow these steps:
  1. Use the `/api/v1/admin/create` endpoint to create a new admin user by providing user details including username (email) and password.
  2. After creating the admin user, use the `/auth/login` endpoint to obtain the JWT token for authentication.
- **Manage Books:** To add, update, or delete books from the catalog, use the following endpoints:

    - **Add Book:** 
        - Method: `POST`
        - Endpoint: `/api/books`
        - Body: Provide the details of the book to be added in the request body.

    - **Update Book:** 
        - Method: `PUT`
        - Endpoint: `/api/books/{id}`
        - Path Parameter: `id` - The ID of the book to be updated.
        - Body: Provide the updated details of the book in the request body.

    - **Delete Book:** 
        - Method: `DELETE`
        - Endpoint: `/api/books/{id}`
        - Path Parameter: `id` - The ID of the book to be deleted.

    - **Fetch Book List:** 
        - Method: `GET`
        - Endpoint: `/api/books`
        - Response: Returns a list of all books in the catalog.

    - **Fetch Book by ID:** 
        - Method: `GET`
        - Endpoint: `/api/books/{id}`
        - Path Parameter: `id` - The ID of the book to fetch.
        - Response: Returns the book details for the specified ID.
- **Manage Borrowers:** To register new borrowers, update their information, or delete their accounts, use the following endpoints:

    - **Register New Borrower:** 
        - Method: `POST`
        - Endpoint: `/api/patrons`
        - Body: Provide the details of the borrower to be registered in the request body.

    - **Update Borrower Information:** 
        - Method: `PUT`
        - Endpoint: `/api/patrons/{id}`
        - Path Parameter: `id` - The ID of the borrower to be updated.
        - Body: Provide the updated details of the borrower in the request body.

    - **Delete Borrower Account:** 
        - Method: `DELETE`
        - Endpoint: `/api/patrons/{id}`
        - Path Parameter: `id` - The ID of the borrower whose account is to be deleted.

    - **Fetch Borrower List:** 
        - Method: `GET`
        - Endpoint: `/api/patrons`
        - Response: Returns a list of all registered borrowers.

    - **Fetch Borrower by ID:** 
        - Method: `GET`
        - Endpoint: `/api/patrons/{id}`
        - Path Parameter: `id` - The ID of the borrower to fetch.
        - Response: Returns the borrower details for the specified ID.
- **Borrow and Return Books:** To borrow and return books, use the following endpoints:

    - **Borrow Book:** 
        - Method: `POST`
        - Endpoint: `/api/borrow/{bookId}/patron/{patronId}`
        - Path Parameters: 
            - `bookId` - The ID of the book to be borrowed.
            - `patronId` - The ID of the borrower who is borrowing the book.

    - **Return Book:** 
        - Method: `PUT`
        - Endpoint: `/api/return/{bookId}/patron/{patronId}`
        - Path Parameters: 
            - `bookId` - The ID of the book to be returned.
            - `patronId` - The ID of the borrower who is returning the book.


## Contributing

Contributions to the Library Management System are welcome! If you find any bugs or have suggestions for improvements, please open an issue or submit a pull request on GitHub.

## License

This project is licensed under the [MIT License](LICENSE).

## Authors

- [John Enyinwa](https://github.com/JEN-dotcom)
