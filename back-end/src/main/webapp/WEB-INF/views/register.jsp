<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
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
        transform: scale(0.98);
        transition: transform 0.3s ease-out;
    }

    .container:hover {
        transform: scale(1);
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

    input, select {
        width: 100%;
        padding: 12px;
        margin-bottom: 18px;
        border: 1px solid #ddd;
        border-radius: 8px;
        font-size: 1rem;
        transition: all 0.3s ease;
        background-color: #f9f9f9;
    }

    input:focus, select:focus {
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

        input, select, button {
            font-size: 0.9rem;
        }
    }
</style>
</head>
<body>
    <div class="container">
        <h2>Register</h2>
        <form id="registerForm" action="/api/auth/register" method="post">
            <!-- Step 1 -->
            <div id="step1">
                <label for="firstName">First Name:</label>
                <input type="text" name="firstName" id="firstName" placeholder="Enter first name" required minlength="3">

                <label for="lastName">Last Name:</label>
                <input type="text" name="lastName" id="lastName" placeholder="Enter last name" required minlength="3">

                <label for="email">Email:</label>
                <input type="email" name="email" id="email" placeholder="Enter email" required>

                <label for="phoneNumber">Phone Number:</label>
                <input type="text" name="phoneNumber" id="phoneNumber" placeholder="Enter phone number" required pattern="^[0-9]{10}$">

                <label for="dateOfBirth">Date of Birth:</label>
                <input type="date" name="dateOfBirth" id="dateOfBirth">

                <button type="button" onclick="showStep2()">Continue</button>
            </div>

            <!-- Step 2 -->
            <div id="step2" style="display: none;">
                <label for="userName">Username:</label>
                <input type="text" name="userName" id="userName" placeholder="Enter username" required minlength="3">

                <label for="password">Password:</label>
                <input type="password" name="password" id="password" placeholder="Enter password" 
                    required pattern="^(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-\\[\\]{}|:;\'<>,.?/]).{8,}$">

                <label for="role">Role:</label>
                <select name="role" id="role" required>
                    <option value="USER" selected>User</option>
                    <option value="ADMIN">Admin</option>
                </select>

                <button type="submit">Register</button>
            </div>

            <!-- Error Display -->
            <% if (request.getAttribute("error") != null) { %>
                <div class="error">
                    <p><%= request.getAttribute("error") %></p>
                </div>
            <% } %>
        </form>
    </div>

    <script>
        function showStep2() {
            // Form input validation for step 1
            const firstName = document.getElementById('firstName').value;
            const lastName = document.getElementById('lastName').value;
            const email = document.getElementById('email').value;
            const phoneNumber = document.getElementById('phoneNumber').value;

            const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            const phonePattern = /^[0-9]{10}$/;

            if (!firstName || !lastName || !email || !phoneNumber) {
                alert('Please fill out all fields.');
                return;
            }

            if (!emailPattern.test(email)) {
                alert('Please enter a valid email address.');
                return;
            }

            if (!phonePattern.test(phoneNumber)) {
                alert('Please enter a valid phone number.');
                return;
            }

            // Show step 2
            document.getElementById('step1').style.display = 'none';
            document.getElementById('step2').style.display = 'block';
        }
    </script>
</body>
</html>
