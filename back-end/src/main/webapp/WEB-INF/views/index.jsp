<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat Application</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/styles.css">
</head>
<body>
    <div id="chat-app">
        <!-- Sidebar -->
        <div id="sidebar">
            <h3>Contacts</h3>
            <div id="contact-list">
                <div class="contact" onclick="selectChat('User1')">User1</div>
                <div class="contact" onclick="selectChat('User2')">User2</div>
            </div>
        </div>

        <!-- Chat Area -->
        <div id="chat-area">
            <div id="chat-header">
                <h3 id="chat-with">Select a user to chat</h3>
            </div>
            <div id="message-box"></div>
            <div id="input-box">
                <input type="text" id="message-input" placeholder="Type a message..." />
                <button onclick="sendMessage()">Send</button>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/script.js"></script>
</body>
</html>
