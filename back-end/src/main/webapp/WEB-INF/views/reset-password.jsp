<%-- <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <style>
        /* Global Styling */
        body {
            font-family: 'Poppins', Arial, sans-serif;
            background: linear-gradient(135deg, #ff7e5f, #feb47b);
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 16px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 450px;
            animation: fadeIn 1s ease-in-out;
        }

        .container h2 {
            text-align: center;
            margin-bottom: 25px;
            font-size: 2rem;
            color: #333;
            font-weight: 600;
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: 600;
            font-size: 1rem;
            color: #555;
        }

        input {
            width: 100%;
            padding: 12px;
            margin-bottom: 18px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s ease;
            background-color: #f9f9f9;
        }

        input:focus {
            border-color: #ff7e5f;
            box-shadow: 0 0 8px rgba(255, 126, 95, 0.6);
            background-color: #fff;
            outline: none;
        }

        button {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #ff7e5f, #feb47b);
            color: #fff;
            border: none;
            border-radius: 8px;
            font-size: 1.2rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        button:hover {
            background: linear-gradient(135deg, #feb47b, #ff7e5f);
            transform: translateY(-2px);
        }

        button:active {
            transform: translateY(2px);
            background: linear-gradient(135deg, #ff6f60, #e6865d);
        }

        .error {
            color: #fff;
            background: #f44336;
            padding: 12px;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 20px;
            font-size: 1rem;
            font-weight: 500;
        }

        /* Animation for fadeIn */
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Responsive Styling */
        @media (max-width: 480px) {
            .container {
                padding: 20px;
            }

            .container h2 {
                font-size: 1.6rem;
            }

            input, button {
                font-size: 0.9rem;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Reset Password</h2>
        
        <!-- Display error message from the backend -->
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form id="resetForm" action="/api/auth/reset-password" method="post" onsubmit="return validateForm()">
            <div id="errorMessage" class="error" style="display: none;">Error message will be shown here.</div>
            
            <label for="token">Enter reset token:</label>
            <input type="text" id="token" name="resetToken" placeholder="Enter reset token" required>
            
            <label for="password">Enter new password:</label>
            <input type="password" id="password" name="newPassword" placeholder="Enter new password" required>
            
            <label for="confirmPassword">Confirm new password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" required>
            
            <button type="submit">Submit</button>
        </form>
    </div>

    <script>
        function validateForm() {
            const token = document.getElementById('token').value;
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            // Basic validation
            if (!token) {
                showError('Reset token is required');
                return false;
            }

            if (password !== confirmPassword) {
                showError('Passwords do not match');
                return false;
            }

            if (password.length < 8) {
                showError('Password must be at least 8 characters long');
                return false;
            }

            // Proceed with the form submission if everything is valid
            return true;
        }

        function showError(message) {
            const errorMessageDiv = document.getElementById('errorMessage');
            errorMessageDiv.style.display = 'block';
            errorMessageDiv.textContent = message;
        }
    </script>
</body>
</html> --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <style>
        /* Global Styling */
body {
    font-family: 'Poppins', Arial, sans-serif;
    background: linear-gradient(135deg, #ff7e5f, #feb47b);
    color: #333;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
}

.container {
    background-color: #fff;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 500px;
    animation: fadeIn 1s ease-in-out;
}

.container h2 {
    text-align: center;
    margin-bottom: 30px;
    font-size: 2.2rem;
    color: #333;
    font-weight: 700;
    letter-spacing: 1px;
}

label {
    display: block;
    margin-bottom: 12px;
    font-weight: 600;
    font-size: 1.1rem;
    color: #555;
}

input {
    width: 100%;
    padding: 15px;
    margin-bottom: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    font-size: 1.1rem;
    transition: all 0.3s ease;
    background-color: #f9f9f9;
    box-sizing: border-box;
}

input:focus {
    border-color: #ff7e5f;
    box-shadow: 0 0 8px rgba(255, 126, 95, 0.6);
    background-color: #fff;
    outline: none;
}

button {
    width: 100%;
    padding: 16px;
    background: linear-gradient(135deg, #ff7e5f, #feb47b);
    color: #fff;
    border: none;
    border-radius: 8px;
    font-size: 1.3rem;
    font-weight: 700;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

button:hover {
    background: linear-gradient(135deg, #feb47b, #ff7e5f);
    transform: translateY(-3px);
}

button:active {
    transform: translateY(3px);
    background: linear-gradient(135deg, #ff6f60, #e6865d);
}

.error-message {
    color: #fff;
    background: #f44336;
    padding: 14px;
    border-radius: 8px;
    text-align: center;
    margin-bottom: 20px;
    font-size: 1.1rem;
    font-weight: 500;
}

/* Animation for fadeIn */
@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-30px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* Responsive Styling */
@media (max-width: 480px) {
    .container {
        padding: 25px;
    }

    .container h2 {
        font-size: 1.8rem;
    }

    input, button {
        font-size: 1rem;
    }
}

    </style>
</head>
<body>
    <div class="container">
        <h2>Reset Password</h2>

        <!-- Display error message if available -->
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message">
                <p><%= request.getAttribute("error") %></p>
            </div>
        <% } %>

        <form id="resetForm" action="/api/auth/reset-password" method="post" onsubmit="return validateForm(event)">
            <div id="clientErrorMessage" class="error" style="display: none;"></div>
            
            <label for="token">Enter reset token:</label>
            <input type="text" id="token" name="resetToken" placeholder="Enter reset token" required>
            
            <label for="password">Enter new password:</label>
            <input type="password" id="password" name="newPassword" placeholder="Enter new password" required>
            
            <label for="confirmPassword">Confirm new password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" required>
            
            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>

