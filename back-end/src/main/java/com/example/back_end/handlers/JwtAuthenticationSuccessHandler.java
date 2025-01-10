// package com.example.back_end.handlers;

// import java.io.IOException;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.stereotype.Component;

// import com.example.back_end.services.JwtService;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//     @Autowired
//     private JwtService jwtUtil;

//     @Override
//     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//         String username = authentication.getName();
//         String token = jwtUtil.generateToken(username);

//         // response.setContentType("application/json");
//         // response.setCharacterEncoding("UTF-8");
//         response.getWriter().write("{\"token\": \"" + token + "\"}");
//         response.setHeader("Authorization", "Bearer " + token);
//         response.setStatus(HttpServletResponse.SC_OK);
//     }
// }
