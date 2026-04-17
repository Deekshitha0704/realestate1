package com.realestate.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String firstName;
    private String lastName;
    private String phone;
    
    @Column(nullable = false)
    private String password;
    
    private String userType; // BUYER, SELLER, AGENT
    private String profileImageUrl;
    private String bio;
    
    private LocalDateTime registrationDate;
    private LocalDateTime lastLoginDate;
    
    private Boolean isActive = true;
    private Boolean isVerified = false;
    
    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
    }
}
