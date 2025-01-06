package com.example.back_end.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.back_end.enums.AccountStatus;
import com.example.back_end.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name is required")
    @Column(columnDefinition = "VARCHAR(30)")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Column(columnDefinition = "VARCHAR(30)")
    private String lastName;

    @NotNull(message = "Username is required")
    @Column(unique = true, columnDefinition = "VARCHAR(20)")
    private String userName;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-\\[\\]{}|:;\"'<>,.?/]).{8,}$",
                message = "Password must contain at least one special character and one digit")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(unique = true)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    private String resetToken;

    private LocalDateTime tokenExpiry;

    private LocalDate dateOfBirth;

    // Default constructor
    public User() {
        this.accountStatus = AccountStatus.ACTIVE;  // Default values
        this.role = Role.USER;
    }

    // Constructor with core fields
    public User(String firstName, String lastName, String userName, String password, Role role, String email,
            String phoneNumber, AccountStatus accountStatus, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role != null ? role : Role.USER;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountStatus = accountStatus != null ? accountStatus : AccountStatus.ACTIVE;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and Setters
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
                + ", accountStatus=" + accountStatus + ", role=" + role + ", email=" + email + ", tokenExpiry="
                + tokenExpiry + ", dateOfBirth=" + dateOfBirth + "]";
    }
}
