package com.example.back_end.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.back_end.models.ChatMessage;
import com.example.back_end.services.ChatMessageService;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/")
    public String index() {
        return "chat.html";  // This will return the 'chat.html' located in 'src/main/resources/static/'
    }

    // Send message to a group
    @MessageMapping("/sendGroupMessage")
    public void sendGroupMessage(String groupName, String sender, String content) {
        ChatMessage chatMessage = chatMessageService.sendMessageToGroup(sender, groupName, content);
        messagingTemplate.convertAndSend("/topic/group/" + groupName, chatMessage);
    }

    // Send private message
    @MessageMapping("/sendPrivateMessage")
    public void sendPrivateMessage(String sender, String receiver, String content) {
        ChatMessage chatMessage = chatMessageService.sendPrivateMessage(sender, receiver, content);
        messagingTemplate.convertAndSend("/topic/private/" + receiver, chatMessage);
    }

    // Get recent messages from a group
    @GetMapping("/api/messages/group/{groupName}")
    public List<ChatMessage> getRecentMessagesForGroup(@PathVariable String groupName) {
        return chatMessageService.getRecentMessagesByGroup(groupName);
    }

    // Get recent private messages between users
    @GetMapping("/api/messages/private/{sender}/{receiver}")
    public List<ChatMessage> getRecentPrivateMessages(@PathVariable String sender, @PathVariable String receiver) {
        return chatMessageService.getRecentPrivateMessages(sender, receiver);
    }
}
