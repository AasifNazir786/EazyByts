# Architectural Decision Record (ADR)

## Title
Architectural Decision for Real-Time Chat Application

## Status
Accepted

## Context
The project is a real-time chat application that requires the following features:
- Real-time messaging between users.
- Secure user authentication.
- A simple and responsive user interface.

The backend will be developed using Java Spring Boot, and the frontend will use HTML, CSS, and JavaScript. We need to decide on the technologies and architecture to implement the core features, particularly real-time communication and authentication.

## Decision
1. **Backend:** Use Java Spring Boot to handle:
   - WebSocket communication for real-time messaging.
   - REST APIs for user authentication and message history.
2. **Frontend:** Use HTML, CSS, and JavaScript to:
   - Create a responsive user interface.
   - Integrate WebSocket for sending and receiving messages.
   - Consume REST APIs for authentication and message history.
3. **Authentication:** Implement user authentication using JSON Web Tokens (JWT).
4. **Database:** Use PostgreSQL for managing user data and storing message history.

## Consequences
### Positive Consequences
- WebSocket provides low-latency real-time communication, enhancing the user experience.
- Spring Boot simplifies backend development and integrates well with WebSocket and PostgreSQL.
- HTML, CSS, and JavaScript offer flexibility for building a custom frontend.
- JWT provides a stateless and scalable way to handle user authentication.

### Negative Consequences
- WebSocket implementation may require additional testing and debugging.
- Ensuring secure WebSocket communication will add complexity.
- Managing frontend state with pure JavaScript (without frameworks like React or Angular) may increase development effort.

## Alternatives Considered
1. **Polling Instead of WebSocket**
   - **Rejected because:** Polling is inefficient and increases server load.
2. **Firebase for Backend**
   - **Rejected because:** The goal is to learn and implement a custom backend using Spring Boot.
3. **Using MySQL Instead of PostgreSQL**
   - **Rejected because:** PostgreSQL offers better support for complex queries and JSON data handling.
4. **Adding a Frontend Framework (React/Angular)**
   - **Rejected because:** The project scope focuses on basic frontend development without additional frameworks.

## Rationale
- WebSocket is the most efficient option for real-time messaging due to its persistent connection and low latency.
- Spring Boot is a robust and familiar backend framework that supports WebSocket and JWT authentication.
- HTML, CSS, and JavaScript are sufficient for building a simple frontend for this project.
- PostgreSQL is chosen for its reliability and compatibility with Spring Boot.

## Implementation Plan
1. **Backend**
   - Set up WebSocket endpoints in Spring Boot for real-time communication.
   - Implement REST APIs for user authentication and message history.
   - Use Spring Security to implement JWT-based authentication.
   - Configure PostgreSQL as the database for storing user data and messages.

2. **Frontend**
   - Design a responsive UI using HTML and CSS.
   - Use JavaScript to:
     - Connect to the WebSocket endpoint.
     - Handle sending and receiving messages in real-time.
     - Consume REST APIs for login, registration, and fetching message history.

3. **Security**
   - Secure WebSocket communication with JWT.
   - Ensure sensitive data is encrypted and securely transmitted.

## Date
January 5, 2025

## Author(s)
Aasif Nazir

