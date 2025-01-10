package com.example.back_end.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.back_end.custom_exceptions.UserNotFoundException;
import com.example.back_end.models.User;
import com.example.back_end.models.UserPrinciple;
import com.example.back_end.repositories.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Try to find the user by username
        User user = userRepository.findByUserName(username)
            .orElseThrow(() -> {
                // Log the exception for debugging purposes
                logger.error("User not found: {}", username);
                return new UserNotFoundException("User not found: " + username);
            });

        // Log successful user loading
        logger.info("User loaded: {}", username);

        // Return UserPrinciple object which implements UserDetails
        return new UserPrinciple(user);
    }
}
