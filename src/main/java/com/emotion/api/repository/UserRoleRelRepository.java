package com.emotion.api.repository;

import com.emotion.api.entity.UserRoleRelEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRoleRelRepository extends PagingAndSortingRepository<UserRoleRelEntity, Long> {
    @Transactional
    void deleteByRoleIdAndUserId(long roleId, long userId);

    List<UserRoleRelEntity> findByUserId (long userId);
}
