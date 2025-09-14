# movie-boooking-application
A backend application for managing movie ticket bookings, built with Spring Boot. This project demonstrates secure RESTful API development using modern Java technologies and best practices.

## Features

- User registration and authentication (JWT-based)
- Book, cancel, and view movie tickets
- Role-based access control with Spring Security
- Data persistence using JPA and Hibernate
- API testing and validation via Postman
- (Coming Soon) Swagger UI for interactive API documentation

## Tech Stack

| Technology       | Purpose                             |
|------------------|-------------------------------------|
| Java + Spring Boot | Backend framework                 |
| Spring Security  | Authentication & Authorization      |
| JWT              | Secure token-based login            |
| JPA + Hibernate  | ORM and database interaction        |
| Postman          | API testing                         |
| Swagger UI       | API documentation (planned)         |

## Project Structure

src/ 
├── main/ 
│ ├── java/ 
│ │ └── com.moviebooking/ 
│ │ ├── controller/ 
│ │ ├── service/ 
│ │ ├── model/ 
│ │ ├── repository/ 
│ │ └── config/ 
│ └── resources/ 
│ ├── application.properties 
│ └── static/ 
└── test/

Code

## Authentication

- JWT tokens are issued upon successful login.
- Protected endpoints require a valid token in the `Authorization` header.
- Role-based access ensures only authorized users can perform specific actions.

## API Testing

All endpoints have been tested using Postman. Sample collections and environment files can be found in the `/postman` folder (if included).

## Swagger Integration (Planned)

To enhance developer experience, Swagger UI will be added for:
- Visualizing and testing endpoints
- Auto-generating API documentation

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/movie-booking-app.git
   cd movie-booking-app
   ```
Configure your database in application.properties.

2. Run the application:

```bash
mvn spring-boot:run
```
Access the API via Postman or Swagger (once integrated).

## Future Enhancements
- Add Swagger UI for better API visibility
