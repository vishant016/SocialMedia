package com.socialmedia.nicheplatform.userManagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Setter
    @Getter
    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Setter
    @Getter
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Setter
    @Getter
    @Column(unique = true, length = 255)
    private String username;

    @Setter
    @Getter
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Setter
    @Getter
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @Setter
    @Getter
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Setter
    @Getter
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Setter
    @Getter
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Setter
    @Getter
    @Column(nullable = false, length = 20)
    private String status = "active";


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Getter
    @Setter
    private Set<UserRole> roles;

}
