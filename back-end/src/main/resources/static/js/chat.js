// Connect to WebSocket
function connect() {
    const socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
    });
}

// Send message to a group
function sendGroupMessage() {
    const groupName = document.getElementById("groupInput").value;
    const message = document.getElementById("messageInput").value;
    if (message && groupName && stompClient) {
        stompClient.send("/app/sendGroupMessage", {}, JSON.stringify({
            groupName: groupName,
            sender: 'User',
            content: message
        }));
        document.getElementById("messageInput").value = '';
    }
}

// Send private message
function sendPrivateMessage() {
    const receiver = document.getElementById("receiverInput").value;
    const message = document.getElementById("messageInputPrivate").value;
    if (message && receiver && stompClient) {
        stompClient.send("/app/sendPrivateMessage", {}, JSON.stringify({
            sender: 'User1',
            receiver: receiver,
            content: message
        }));
        document.getElementById("messageInputPrivate").value = '';
    }
}

// Subscribe to group chat
function subscribeToGroup(groupName) {
    stompClient.subscribe('/topic/group/' + groupName, function(message) {
        const messageBody = JSON.parse(message.body);
        displayGroupMessage(messageBody);
    });
}

// Subscribe to private chat
function subscribeToPrivate(receiver) {
    stompClient.subscribe('/topic/private/' + receiver, function(message) {
        const messageBody = JSON.parse(message.body);
        displayPrivateMessage(messageBody);
    });
}

// Display group message in the browser
function displayGroupMessage(message) {
    const groupMessagesDiv = document.getElementById("groupMessages");
    const messageElement = document.createElement("div");
    messageElement.textContent = message.sender + ": " + message.content;
    groupMessagesDiv.appendChild(messageElement);
}

// Display private message in the browser
function displayPrivateMessage(message) {
    const privateMessagesDiv = document.getElementById("privateMessages");
    const messageElement = document.createElement("div");
    messageElement.textContent = message.sender + ": " + message.content;
    privateMessagesDiv.appendChild(messageElement);
}

// Event listeners
document.getElementById("sendGroupButton").onclick = sendGroupMessage;
document.getElementById("sendPrivateButton").onclick = sendPrivateMessage;

window.onload = connect;
