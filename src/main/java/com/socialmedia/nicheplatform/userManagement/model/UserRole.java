package com.socialmedia.nicheplatform.userManagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class)
public class UserRole {
    @Id
    @Column(name = "user_id", length = 36)
    private String userId;

    @Getter
    @Setter
    @Id
    @Column(name = "role_name", length = 50)
    private String roleName;

    @Getter
    @Setter
    @Column(name = "assigned_at", nullable = false)
    private Timestamp assignedAt = new Timestamp(System.currentTimeMillis());

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    public UserRole(String userId, String roleName) {
        this.userId = userId;
        this.roleName = roleName;
    }

    public UserRole() {

    }
}
