package com.example.back_end.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back_end.models.ChatMessage;
import com.example.back_end.repositories.ChatMessageRepository;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // Send message to a group
    public ChatMessage sendMessageToGroup(ChatMessage chatMessage) {
        chatMessage.setTimeStamp(LocalDateTime.now());
        return chatMessageRepository.save(chatMessage);
    }

    // Send private message
    public ChatMessage sendPrivateMessage(ChatMessage chatMessage) {
        chatMessage.setTimeStamp(LocalDateTime.now());
        return chatMessageRepository.save(chatMessage);
    }

    // Get recent messages by group
    public List<ChatMessage> getRecentMessagesByGroup(String groupName) {
        return chatMessageRepository.findByGroupName(groupName)
        .stream()
        .sorted((m1, m2) -> m2.getTimeStamp().compareTo(m1.getTimeStamp()))
        .collect(Collectors.toList());
    }

    // Get recent private messages
    public List<ChatMessage> getRecentPrivateMessages(String sender, String receiver) {
        return chatMessageRepository.findBySenderAndReceiver(sender, receiver).stream()
                .sorted((m1, m2) -> m2.getTimeStamp().compareTo(m1.getTimeStamp()))
                .collect(Collectors.toList());
    }

    // Fetch all messages
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }


}
