package com.example.back_end.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_end.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}

