package com.emotion.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "username", nullable = false)
    private String username;
    @Builder.Default
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled = false;
    @Builder.Default
    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;
    @Builder.Default
    @Column(name = "withdraw", nullable = false)
    private boolean withdraw = false;
    @Column(name = "last_login_dt", nullable = false)
    private java.sql.Timestamp lastLoginDt;
    @Column(name = "create_dt", insertable = false, updatable = false, nullable = false)
    private java.sql.Timestamp createDt;
    @Column(name = "create_user_id", updatable = false, nullable = false)
    private long createUserId;
    @Column(name = "update_dt", insertable = false, updatable = false, nullable = false)
    private java.sql.Timestamp updateDt;
    @Column(name = "update_user_id", nullable = false)
    private long updateUserId;
}