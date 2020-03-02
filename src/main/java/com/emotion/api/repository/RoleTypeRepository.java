package com.emotion.api.repository;

import com.emotion.api.entity.RoleTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleTypeRepository extends PagingAndSortingRepository<RoleTypeEntity, Long> {
    @Query("select r from RoleTypeEntity r where r.roleId in (select urr.roleId from UserRoleRelEntity urr where urr.userId = :userId)")
    List<RoleTypeEntity> findByRoleTypeUserRoleRelEntityUserId(@Param("userId") long userId);
}
