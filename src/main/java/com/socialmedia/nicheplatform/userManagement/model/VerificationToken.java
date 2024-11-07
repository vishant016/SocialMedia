package com.socialmedia.nicheplatform.userManagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "verificationtokens")
public class VerificationToken {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String token;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @Setter
    private Date expiryDate;


}
