package com.emotion.api.security;

import com.nimbusds.oauth2.sdk.TokenRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

public class CustomTokenService extends DefaultTokenServices {

    public CustomTokenService() {
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        return super.readAccessToken(accessToken);
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        OAuth2AccessToken token = super.createAccessToken(authentication);
        return token;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest)
            throws AuthenticationException {

        OAuth2AccessToken token = super.refreshAccessToken(refreshTokenValue, tokenRequest);
        return token;
    }
}