package com.emotion.api.service;

import com.emotion.api.model.RoleTypeEntity;
import com.emotion.api.model.UserEntity;
import com.emotion.api.repository.RoleTypeRepository;
import com.emotion.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    JwtService jwtService;

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

}
