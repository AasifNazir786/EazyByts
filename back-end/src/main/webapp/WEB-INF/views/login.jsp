<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <style>
        /* Body Styling */
        body {
            font-family: 'Poppins', Arial, sans-serif;
            background: linear-gradient(135deg, #f06c64, #f9c846);
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* Container Styling */
        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 400px;
            animation: fadeIn 0.8s ease-in-out;
        }

        /* Header Styling */
        .container h2 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 2rem;
            color: #444;
        }

        /* Error Message Styling */
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border: 1px solid #f5c6cb;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 20px;
            font-size: 0.95rem;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        /* Label Styling */
        .container label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            font-size: 1rem;
            color: #555;
        }

        /* Input Styling */
        .container input {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        /* Input Focus Styling */
        .container input:focus {
            border-color: #f06c64;
            outline: none;
            box-shadow: 0 0 5px rgba(240, 108, 100, 0.5);
        }

        /* Button Styling */
        .container button {
            width: 100%;
            padding: 14px;
            background-color: #f06c64;
            color: #fff;
            border: none;
            border-radius: 8px;
            font-size: 1.1rem;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
        }

        /* Button Hover Effect */
        .container button:hover {
            background-color: #e0554c;
            transform: scale(1.02);
        }

        /* Paragraph and Links Styling */
        .container p {
            text-align: center;
            font-size: 0.9rem;
            color: #555;
            margin: 15px 0 0;
        }

        .container p a {
            color: #f06c64;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s;
        }

        .container p a:hover {
            color: #c94b43;
        }

        /* Responsive Design */
        @media (max-width: 500px) {
            .container {
                padding: 20px;
                width: 90%;
            }
        }

        /* Animation for Container */
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Login</h2>

        <!-- Display error message if available -->
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message">
                <p><%= request.getAttribute("error") %></p>
            </div>
        <% } %>

        <form action="/api/auth/login" method="post">
            <!-- CSRF token -->
            <input type="hidden" name="_csrf" value="${_csrf.token}">

            <!-- Username -->
            <label for="username">Username:</label>
            <input type="text" name="userName" id="username" placeholder="Enter your username"
                aria-label="Username" required>

            <!-- Password -->
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" placeholder="Enter your password"
                aria-label="Password" required>

            <!-- Submit Button -->
            <button type="submit">Login</button>

            <!-- Additional Links -->
            <p><a href="/api/auth/forgot-password-form">Forgot Password?</a></p>
            <p>Don't have an account? <a href="/api/auth/sign-up">Sign Up</a></p>
        </form>
    </div>
</body>
</html>
