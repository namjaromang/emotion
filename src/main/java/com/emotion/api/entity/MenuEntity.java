package com.emotion.api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "menu")
public class MenuEntity {
    @Id
    @Column(name = "menu_id", nullable = false)
    private long menuId;
    @Column(name = "menu_name", nullable = false)
    private String menuName;
    @Column(name = "menu_dtl_name", nullable = false)
    private String menuDtlName;
    @Column(name = "scope_name", nullable = false)
    private String scopeName;
    @Column(name = "create_dt", insertable = false, updatable = false, nullable = false)
    private java.sql.Timestamp createDt;
    @Column(name = "create_user_id", updatable = false, nullable = false)
    private long createUserId;
    @Column(name = "update_dt", insertable = false, updatable = false, nullable = false)
    private java.sql.Timestamp updateDt;
    @Column(name = "update_user_id", nullable = false)
    private long updateUserId;

}
