package com.emotion.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user_role_rel")
public class UserRoleRelEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_role_rel_id", nullable = false)
    private long userRoleRelId;
    @Column(name = "role_id", nullable = false)
    private long roleId;
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Column(name = "create_dt", insertable = false, updatable = false, nullable = false)
    private java.sql.Timestamp createDt;
    @Column(name = "create_user_id", updatable = false, nullable = false)
    private long createUserId;
    @Column(name = "update_dt", insertable = false, updatable = false, nullable = false)
    private java.sql.Timestamp updateDt;
    @Column(name = "update_user_id", nullable = false)
    private long updateUserId;
}

