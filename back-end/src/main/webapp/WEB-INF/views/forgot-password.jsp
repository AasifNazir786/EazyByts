<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password Request</title>
    <style>
        /* Reset CSS */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            background-color: #f0f4f8; /* Light background color */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: #ffffff; /* White background for the form */
            padding: 20px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* Light shadow */
            width: 100%;
            max-width: 400px; /* Limit the max width */
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 1.8rem;
            color: #333; /* Dark gray for the heading */
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-size: 1rem;
            color: #555; /* Medium gray for labels */
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 10px 12px;
            margin-bottom: 20px;
            border: 1px solid #ddd; /* Light gray border */
            border-radius: 6px;
            font-size: 1rem;
            color: #333;
        }

        input:focus {
            border-color: #007bff; /* Blue border on focus */
            outline: none;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* Slight shadow */
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #007bff; /* Primary blue button */
            color: #fff; /* White text */
            border: none;
            border-radius: 6px;
            font-size: 1rem;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #0056b3; /* Darker blue on hover */
        }

        button:focus {
            outline: none;
            box-shadow: 0 0 5px rgba(0, 91, 179, 0.5); /* Blue shadow */
        }

        /* Responsive Design */
        @media (max-width: 600px) {
            h1 {
                font-size: 1.5rem;
            }

            .container {
                padding: 15px;
            }

            button {
                font-size: 0.9rem;
                padding: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Reset Password Request</h1>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message">
                <p><%= request.getAttribute("error") %></p>
            </div>
        <% } %>

        <form id="reset-password-form" action="/api/auth/forgot-password" method="post">

            <label for="emailOrPhone">Enter email or phone</label>
            <input type="email" id="emailOrPhone" name="emailOrPhone" placeholder="Enter Email or Phone" required>
            
            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>
