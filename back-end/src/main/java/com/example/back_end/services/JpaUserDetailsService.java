// package com.example.back_end.services;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.stereotype.Service;

// import com.example.back_end.custom_exceptions.UserNotFoundException;
// import com.example.back_end.models.User;
// import com.example.back_end.models.UserPrinciple;
// import com.example.back_end.repositories.UserRepository;

// @Service
// public class JpaUserDetailsService implements UserDetailsService{

//     private final UserRepository userRepository;

//     public JpaUserDetailsService(UserRepository userRepository){
//         this.userRepository = userRepository;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) {

//         User user = userRepository.findByUserName(username)
//             .orElseThrow(() -> new UserNotFoundException("user not found" + username));

//         return new UserPrinciple(user);
//     }
    
// }
