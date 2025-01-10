<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        h1 {
            font-size: 2.5rem;
            color: #333;
            background: linear-gradient(45deg, #ff7e5f, #feb47b);
            -webkit-background-clip: text;
            color: transparent;
            text-align: center;
        }

        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border: 1px solid #f5c6cb;
            border-radius: 8px;
            text-align: center;
            font-size: 1rem;
            margin-bottom: 20px;
            max-width: 400px;
            margin: 20px auto;
        }

        .container {
            text-align: center;
            width: 100%;
            max-width: 600px;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .user-info {
            background-color: #e0ffe0;
            padding: 15px;
            border-radius: 8px;
            margin-top: 20px;
            font-size: 1.2rem;
            color: #2c3e50;
        }

        .btn-logout {
            background-color: #ff6f61;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 1rem;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-top: 20px;
        }

        .btn-logout:hover {
            background-color: #e55b50;
        }

        /* Responsive Design */
        @media (max-width: 500px) {
            .container {
                padding: 15px;
                width: 90%;
            }
            h1 {
                font-size: 2rem;
            }
        }
    </style>
</head>

<body>

    <div class="container">
        <!-- Error message if the user is not authenticated -->
        <div id="error-message" class="error-message" style="display:none;">
            Please log in to access this page.
        </div>

        <!-- Main Content -->
        <h1>Welcome to the Home Page!</h1>

        <!-- User Information -->
        <div id="user-info" class="user-info" style="display: none;">
            <p id="user-greeting">Loading user data...</p>
            <button class="btn-logout" onclick="logout()">Logout</button>
        </div>
    </div>

    <script>
        // Function to get JWT token from localStorage
        function getToken() {
            return localStorage.getItem('jwt');  // JWT token is stored under 'jwt'
        }

        // Function to verify the JWT token with the backend
        function verifyToken(token) {
            return fetch('/api/auth/verify-token', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`  // Send the token in the Authorization header
                }
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        console.log('Token is valid');
                        // Fetch user details if the token is valid
                        loadUserInfo();
                    } else {
                        throw new Error('Token is invalid or expired');
                    }
                })
                .catch(error => {
                    console.error('Verification error:', error);
                    document.getElementById('error-message').style.display = 'block'; // Show error message
                    setTimeout(() => {
                        window.location.href = '/api/auth/login-form'; // Redirect to login page
                    }, 2000); // Redirect after 2 seconds
                });
        }

        // Function to load user data after successful token verification
        function loadUserInfo() {
            const token = getToken();

            fetch('/api/auth/user-info', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`  // Send token in Authorization header
                }
            })
                .then(response => response.json())
                .then(data => {
                    if (data && data.userName) {
                        // Show user info
                        document.getElementById('user-greeting').innerHTML = `Hello, ${data.userName}!`;
                        document.getElementById('user-info').style.display = 'block';  // Display user info
                    } else {
                        throw new Error('User info is unavailable');
                    }
                })
                .catch(error => {
                    console.error('Error loading user info:', error);
                    document.getElementById('error-message').style.display = 'block'; // Show error message
                    setTimeout(() => {
                        window.location.href = '/api/auth/login-form'; // Redirect to login page
                    }, 2000); // Redirect after 2 seconds
                });
        }

        // Function to check if the user is authenticated
        function checkAuthentication() {
            const token = getToken();

            if (!token) {
                // If token is not found, show an error message and redirect to login
                document.getElementById('error-message').style.display = 'block';
                setTimeout(() => {
                    window.location.href = '/api/auth/login-form'; // Redirect to login page
                }, 2000); // Redirect after 2 seconds
            } else {
                // If token exists, verify it with the backend
                verifyToken(token);
            }
        }

        // Function to log out the user
        function logout() {
            localStorage.removeItem('jwt');  // Remove JWT from localStorage
            window.location.href = '/api/auth/login-form';  // Redirect to login page
        }

        // Call the function to check authentication on page load
        checkAuthentication();
    </script>

</body>

</html>
