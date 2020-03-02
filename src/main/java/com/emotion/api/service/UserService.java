package com.emotion.api.service;

import com.emotion.api.constant.Const;
import com.emotion.api.entity.RoleTypeEntity;
import com.emotion.api.entity.UserEntity;
import com.emotion.api.entity.UserRoleRelEntity;
import com.emotion.api.exception.BusinessException;
import com.emotion.api.exception.ErrorCode;
import com.emotion.api.payload.request.SignUpRequest;
import com.emotion.api.repository.RoleTypeRepository;
import com.emotion.api.repository.UserRepository;
import com.emotion.api.repository.UserRoleRelRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleTypeRepository roleTypeRepository;

    @Autowired
    UserRoleRelRepository userRoleRelRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Map<String, Object> getUserInfo() throws IOException, GeneralSecurityException {
        Map<String, Object> jwtMap = jwtService.getJwtInfo();

        long uid = Long.valueOf((Integer) jwtMap.get("uid"));
        UserEntity userInfo = userRepository.findTopByUserIdOrderByCreateDtDesc(uid);

        Map<String, Object> map = new LinkedHashMap<>();
        map.putAll(jwtMap);
        map.put("lastLoginDt", userInfo.getLastLoginDt());
        map.put("name", userInfo.getUsername());
        List<RoleTypeEntity> roleList = roleTypeRepository.findByRoleTypeUserRoleRelEntityUserId(uid);
        List<String> authority = roleList.stream().map(r -> r.getRoleEngName()).collect(Collectors.toList());
        map.put("authorities", authority);

        return map;
    }

    public UserEntity singup(SignUpRequest signUpRequest) {
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .username(signUpRequest.getUsername())
                .isEnabled(true)
                .build());


        userEntity.setCreateUserId(userEntity.getUserId());
        userEntity.setUpdateUserId(userEntity.getUserId());

        userRoleRelRepository.save(UserRoleRelEntity.builder()
                .roleId(Const.ROLE_USER)
                .userId(userEntity.getUserId())
                .createUserId(userEntity.getUserId())
                .updateUserId(userEntity.getUserId())
                .build());

        return userRepository.save(userEntity);
    }

    public boolean overlapEmail(String email) {
        Boolean emailOverlap = userRepository.existsTopByEmail(email);
        return emailOverlap;
    }

    public void confirmPassword(String password) throws IOException, GeneralSecurityException {
        Map<String, Object> jwtMap = jwtService.getJwtInfo();
        long uid = Long.valueOf((Integer) jwtMap.get("uid"));

        UserEntity userInfo = userRepository.findById(uid).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(password, userInfo.getPassword()))
            throw new BusinessException(ErrorCode.PASSWORD_IS_DIFFERENT);
    }

    public void changePassword(String password) throws IOException, GeneralSecurityException {
        Map<String, Object> jwtMap = jwtService.getJwtInfo();
        long uid = Long.valueOf((Integer) jwtMap.get("uid"));

        UserEntity userInfo = userRepository.findById(uid).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        userInfo.setPassword(passwordEncoder.encode(password));
        userRepository.save(userInfo);
    }

    /**
     * 탈퇴처리
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public void withdraw() throws IOException, GeneralSecurityException {
        Map<String, Object> jwtMap = jwtService.getJwtInfo();
        long uid = Long.valueOf((Integer) jwtMap.get("uid"));

        UserEntity userInfo = userRepository.findById(uid).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        userInfo.setWithdraw(true);
        userInfo.setEnabled(false);
        userRepository.save(userInfo);
    }
}
