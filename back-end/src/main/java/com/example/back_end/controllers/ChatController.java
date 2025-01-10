// package com.example.back_end.controllers;

// import java.time.LocalDateTime;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;

// import com.example.back_end.models.Message;
// import com.example.back_end.services.MessageService;

// @Controller
// public class ChatController {

//     @Autowired
//     private MessageService messageService;

//     @GetMapping("/chat")
//     public String chatPage() {
//         return "chat";
//     }

//     @GetMapping("/messages")
//     public List<Message> getAllMessages() {
//         return messageService.getMessages();
//     }

//     @MessageMapping("/sendMessage")
//     @SendTo("/topic/messages")
//     public Message sendMessage(Message message) {
//         message.setTimestamp(LocalDateTime.now());
//         return messageService.saveMessage(message);
//     }
// }

