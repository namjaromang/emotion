package com.emotion.api.repository;

import com.emotion.api.model.UserRoleRelEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRoleRelRepository extends PagingAndSortingRepository<UserRoleRelEntity, Long> {
    @Transactional
    void deleteByRoleIdAndUserId(long roleId, long userId);

    UserRoleRelEntity findByUserId(long userId);
}
