package com.emotion.api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Entity
@Table(name = "role_type_menu_rel")
public class RoleTypeMenuRelEntity {
    @Id
    @Column(name = "user_type_menu_rel_id", nullable = false)
    private long userTypeMenuRelId;
    @Column(name = "create_dt", insertable = false, updatable = false, nullable = false)
    private java.sql.Timestamp createDt;
    @Column(name = "create_user_id", updatable = false, nullable = false)
    private long createUserId;
    @Column(name = "update_dt", insertable = false, updatable = false, nullable = false)
    private java.sql.Timestamp updateDt;
    @Column(name = "update_user_id", nullable = false)
    private long updateUserId;
    @Column(name = "menu_id", nullable = false)
    private long menuId;
    @Column(name = "role_id", nullable = false)
    private long roleId;

}
