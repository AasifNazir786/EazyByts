package com.example.back_end.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.example.back_end.filters.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    // @Autowired
    // private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    // View resolver for JSP pages.
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    // Bean for password encoding.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    // Bean for AuthenticationProvider.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    
    // Bean for AuthenticationManager.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Security configuration for HTTP requests.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http.csrf(csrf -> csrf.disable());

        // Configure which routes are public and which are protected
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                        "/api/auth/login-form",
                        "/api/auth/login",
                        "/WEB-INF/views/login.jsp"
                ).permitAll()
                .anyRequest()
                .authenticated()
        );

        // Form-based login configuration
        http.formLogin(form -> form
                .loginPage("/api/auth/login-form") // Custom login form URL
                // .loginProcessingUrl("/api/auth/login") // Endpoint for form submission
                // .usernameParameter("userName") // Custom username field
                // .passwordParameter("password") // Custom password field
                // // .successHandler(jwtAuthenticationSuccessHandler) // Use custom success handler
                // .defaultSuccessUrl("/api/auth/home", true) // Redirect after successful login
                // .failureUrl("/api/auth/login-form?error=true") // Redirect on login failure
                .permitAll()
        );

        // Logout configuration
        // http.logout(logout -> logout
        //         .logoutUrl("/api/auth/logout")
        //         .logoutSuccessUrl("/api/auth/login-form?logout=true") // Redirect after logout
        //         .permitAll()
        // );

        // JWT Filter for handling API requests with tokens
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions
        );

        // Add JWT filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }
}
