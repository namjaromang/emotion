package com.emotion.api.repository;

import com.emotion.api.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    boolean existsTopByEmail(@Param("email") String email);
    UserEntity findTopByUserIdOrderByCreateDtDesc(Long userId);
    Optional<UserEntity> findTopByEmailAndWithdrawIsFalse(String email);
}
