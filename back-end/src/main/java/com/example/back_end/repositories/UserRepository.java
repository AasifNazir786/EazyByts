package com.example.back_end.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back_end.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);
    
    Optional<User> findByUserName(String userName);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    Optional<User> findByResetToken(String resetToken);
    
}
