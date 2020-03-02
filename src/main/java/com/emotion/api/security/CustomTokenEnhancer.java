package com.emotion.api.security;

import com.emotion.api.entity.UserEntity;
import com.emotion.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.sql.Timestamp;
import java.util.*;

@AllArgsConstructor
public class CustomTokenEnhancer implements TokenEnhancer {

    private final UserRepository userRepository;


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object obj = authentication.getPrincipal();
        String email = String.valueOf(obj);
        if (obj instanceof User) {
            User user = (User) obj;
            email = user.getUsername();
        }
        UserEntity userEntity = userRepository.findTopByEmailAndWithdrawIsFalse(email).orElse(null);

        Map<String, Object> info = new HashMap();
        info.put("uid", userEntity.getUserId());
        info.put("name", userEntity.getUsername());
        info.put("lastLoginDt", userEntity.getLastLoginDt());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        userEntity.setLastLoginDt(new Timestamp(new Date().getTime()));
        userRepository.save(userEntity);
        return accessToken;
    }
}