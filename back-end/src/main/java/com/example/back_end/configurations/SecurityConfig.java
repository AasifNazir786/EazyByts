package com.example.back_end.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
// @EnableWebSecurity
public class SecurityConfig {

    // private final JpaUserDetailsService myUserDetailsService;

    // public SecurityConfig(JpaUserDetailsService myUserDetailsService) {
    //     this.myUserDetailsService = myUserDetailsService;
    // }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    // @Bean
    // public BCryptPasswordEncoder bCryptPasswordEncoder() {
    //     return new BCryptPasswordEncoder(12);
    // }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     // Disable CSRF if necessary (for now it's commented)
    //     // http.csrf(csrf -> csrf.disable());

    //     // Allow specific routes without authentication
    //     http.authorizeHttpRequests(authorize -> authorize
    //             .requestMatchers("/api/auth/login-form", "/api/auth/sign-up", "/css/**", "/js/**", "/api/auth/forgot-password-form", "/api/auth/reset-password-form") // Allow public access
    //             .permitAll()
    //             .anyRequest()
    //             .authenticated());

    //     // Form login configuration
    //     http.formLogin(form -> form
    //             .loginPage("/api/auth/login-form?error=true") // Custom login page
    //             .loginProcessingUrl("/api/auth/login") // Custom login action URL
    //             .usernameParameter("userName") // Username field name in the form
    //             .passwordParameter("password") // Password field name in the form
    //             .defaultSuccessUrl("/api/auth/home", false) // Redirect after successful login
    //             .permitAll());

    //     // Logout configuration
    //     http.logout(logout -> logout
    //             .logoutUrl("/api/auth/logout")
    //             .logoutSuccessUrl("/api/auth/login-form?logout") // Redirect after logout
    //             .permitAll());
            

    //     return http.build();
    // }

    // @Bean
    // public AuthenticationProvider authenticationProvider(BCryptPasswordEncoder encoder) {
    //     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //     provider.setPasswordEncoder(encoder);
    //     provider.setUserDetailsService(myUserDetailsService); // Use custom UserDetailsService
    //     return provider;
    // }

    // @Bean
    // public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    //     return configuration.getAuthenticationManager();
    // }
}
