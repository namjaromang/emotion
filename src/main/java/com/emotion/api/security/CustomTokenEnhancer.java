package com.emotion.api.security;


import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class CustomTokenEnhancer implements TokenEnhancer {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final StaffDepartmentRelRepository staffDepartmentRelRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object obj = authentication.getPrincipal();
        if (obj == null || !(obj instanceof User)) throw new UsernameNotFoundException("Unauthorized");
        User user = (User) obj;
        UserEntity userEntity = userRepository.findTopByEmailOrderByEmailVerifiedDesc(user.getUsername()).orElse(null);
        StaffEntity staffEntity = staffRepository.findTopByEmail(user.getUsername());

        List<DepartmentEntity> departmentList = departmentRepository.findByStaffDepartmentRelEntityStaffId(staffEntity.getStaffId());
        Map<String, Object> info = new HashMap();
        info.put("uid", userEntity.getUserId());
        info.put("name", userEntity.getUsername());
        info.put("sid", staffEntity.getStaffId());
        info.put("lastLoginDt", userEntity.getLastLoginDt());
        info.put("department", departmentList);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        userEntity.setLastLoginDt(new Timestamp(new Date().getTime()));
        userRepository.save(userEntity);
        return accessToken;
    }
}