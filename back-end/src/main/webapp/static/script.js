const socket = new WebSocket("ws://localhost:8080/chat");
let currentReceiver = null;

// Select a chat to open
function selectChat(username) {
    currentReceiver = username;
    document.getElementById("chat-with").textContent = `Chat with ${username}`;
    loadChatHistory(username);
}

// Load chat history for a particular receiver
function loadChatHistory(username) {
    // Send request to load chat history for currentReceiver
    // Example: You could call an API endpoint to fetch messages
    // For now, we'll simulate it
    document.getElementById("message-box").innerHTML = "";
    // Add previous chat messages here
}

// Send message to WebSocket server
function sendMessage() {
    const messageInput = document.getElementById("message-input");
    const messageContent = messageInput.value;
    if (messageContent && currentReceiver) {
        socket.send(messageContent);
        messageInput.value = ""; // Clear input field after sending
    }
}

// Listen for incoming messages
socket.onmessage = function(event) {
    const messageBox = document.getElementById("message-box");
    const newMessage = document.createElement("div");
    newMessage.textContent = event.data;
    messageBox.appendChild(newMessage);
    messageBox.scrollTop = messageBox.scrollHeight; // Auto scroll to the bottom
};
