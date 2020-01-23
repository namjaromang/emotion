package com.emotion.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Entity
public class RoleTypeEntity {
    @Id
    @Column(name = "role_id", nullable = false)
    private long roleId;
    @Column(name = "role_eng_name", nullable = false)
    private String roleEngName;
    @Column(name = "role_name", nullable = false)
    private String roleName;
    @Column(name = "create_dt", insertable = false, updatable = false, nullable = false)
    private java.sql.Timestamp createDt;
    @Column(name = "create_user_id", updatable = false, nullable = false)
    private long createUserId;
    @Column(name = "update_dt", insertable = false, updatable = false, nullable = false)
    private java.sql.Timestamp updateDt;
    @Column(name = "update_user_id", nullable = false)
    private long updateUserId;
}
