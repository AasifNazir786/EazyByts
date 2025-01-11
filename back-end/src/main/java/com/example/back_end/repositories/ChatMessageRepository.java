package com.example.back_end.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_end.models.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByGroupNameOrderByTimeStampDesc(String groupName);

    List<ChatMessage> findBySenderAndReceiverOrderByTimeStampDesc(String sender, String receiver);

    Collection<ChatMessage> findBySenderAndReceiver(String sender, String receiver);

    List<ChatMessage> findByGroupName(String groupName);
}
