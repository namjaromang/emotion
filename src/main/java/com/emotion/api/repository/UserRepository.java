package com.emotion.api.repository;

import com.emotion.api.model.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    boolean existsTopByEmailAndEmailVerified(@Param("email") String email, @Param("emailVerified") boolean emailVerified);
    UserEntity findTopByEmailAndEmailVerifiedOrderByCreateDtDesc(String email ,boolean emailVerified);
    UserEntity findTopByUserIdOrderByCreateDtDesc(Long userId);
    Optional<UserEntity> findTopByEmailAndIsEnabledIsTrueAndEmailVerifiedIsTrueAndWithdrawIsFalseOrderByEmailVerifiedDesc(String email);
}
