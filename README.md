# Documentation for Real-Time Chat Application

## Overview
The real-time chat application is a platform where users can communicate with each other via instant messages. The application features secure user authentication, real-time messaging, and a simple and responsive user interface. The backend is developed using Java Spring Boot, while the frontend uses HTML, CSS, and JavaScript.

---

## Features
1. **Real-Time Messaging**
   - Persistent WebSocket connections enable instant communication between users.

2. **User Authentication**
   - Secure login and registration using JSON Web Tokens (JWT).

3. **Responsive UI**
   - A clean and intuitive user interface designed using HTML and CSS.

4. **Message History**
   - Users can view their previous messages stored in the database.

---

## Architecture
### Backend
The backend is implemented using Java Spring Boot and handles:
- WebSocket communication for real-time messaging.
- REST APIs for user management and message history.
- Authentication using Spring Security and JWT.
- Database management using PostgreSQL.

### Frontend
The frontend is developed with HTML, CSS, and JavaScript:
- Responsive design using CSS.
- JavaScript to handle WebSocket connections and API interactions.

---

## Technology Stack
1. **Backend:** Java Spring Boot
2. **Frontend:** HTML, CSS, JavaScript
3. **Database:** PostgreSQL
4. **Authentication:** JSON Web Tokens (JWT)
5. **Real-Time Communication:** WebSocket

---

## Setup and Installation
### Prerequisites
- Java 17 or later
- PostgreSQL installed and running
- A web browser

### Steps to Run the Application
1. **Backend Setup:**
   - Clone the repository.
   - Update `application.properties` with your PostgreSQL credentials.
   - Run the Spring Boot application.

2. **Frontend Setup:**
   - Open the `index.html` file in a web browser.

3. **Database Setup:**
   - Create a PostgreSQL database.
   - Run the provided SQL scripts to set up the schema.

---

## API Endpoints
### Authentication
1. **Register User**
   - `POST /api/auth/register`
   - Request Body:
     ```json
     {
        "id": 1,
        "firstName": "Aasif",
        "lastName": "Shah",
        "userName": "Aasif123",
        "password": "Pass@1234",
        "email": "aasif123e@example.com",
        "phoneNumber": "9876543210",
        "dateOfBirth": "01/01/2000",
        "role": "USER",
        "accountStatus": "ACTIVE"
     }
     ```

2. **Login User**
   - `POST /api/auth/login`
   - Request Body:
     ```json
     {
       "username": "exampleUser",
       "password": "examplePassword"
     }
     ```

### Messages
1. **Send Message**
   - WebSocket endpoint: `/ws/message`

2. **Fetch Message History**
   - `GET /api/messages/{userId}`

---

## How It Works
1. **User Registration and Login**
   - Users register by providing a signup details.
   - Upon login, the server generates a JWT, which the user uses for all subsequent requests.

2. **Real-Time Messaging**
   - A WebSocket connection is established between the client and server.
   - Messages are sent and received in real-time through this connection.

3. **Message History**
   - Messages are saved in the PostgreSQL database and can be retrieved via a REST API.

---

## Security Measures
- Passwords are hashed using BCrypt.
- JWTs are used to secure API requests and WebSocket communication.
- WebSocket connections are validated with the JWT.

---

## Future Enhancements
- Add group chat functionality.
- Integrate typing indicators and read receipts.
- Implement a mobile-friendly design.
- Add support for multimedia messages (images, videos).

---

## Contributors
[Aasif Nazir]

---

## License
This project is open-source and available under the [MIT License](LICENSE).
