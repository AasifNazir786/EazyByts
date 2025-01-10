package com.example.back_end.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back_end.models.ChatMessage;
import com.example.back_end.repositories.ChatMessageRepository;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // Send message to a group
    public ChatMessage sendMessageToGroup(String sender, String groupName, String content) {
        ChatMessage message = new ChatMessage(sender, groupName, content, LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    // Send private message to a user
    public ChatMessage sendPrivateMessage(String sender, String receiver, String content) {
        ChatMessage message = new ChatMessage(sender, receiver, content, LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    // Get recent messages by group
    public List<ChatMessage> getRecentMessagesByGroup(String groupName) {
        return chatMessageRepository.findByGroupNameOrderByTimeStampDesc(groupName); // Custom query for recent messages in a group
    }

    // Get recent private messages between users
    public List<ChatMessage> getRecentPrivateMessages(String sender, String receiver) {
        return chatMessageRepository.findBySenderAndReceiverOrderByTimeStampDesc(sender, receiver); // Custom query for private messages
    }

    // Get all messages for a specific sender
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }
}
