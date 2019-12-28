package com.emotion.api.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.AuthenticationManager;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailsService userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffDepartmentRelRepository staffDepartmentRelRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 클라이언트 정보 주입 방식을 jdbcdetail로 변경
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * 추가 정보 관리
     */
    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new CustomTokenEnhancer(userRepository, staffRepository, staffDepartmentRelRepository, departmentRepository), jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new CustomTokenService();
        tokenServices.setTokenStore(new JwtTokenStore(jwtAccessTokenConverter()));
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenEnhancer(tokenEnhancerChain());
        return tokenServices;
    }

    /**
     * 토큰 정보를 DB를 통해 관리한다.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancerChain())
                .tokenStore(new JwtTokenStore(jwtAccessTokenConverter()))
                .userDetailsService(userDetailService)
                .tokenServices(tokenServices());
    }

    /**
     * jwt converter - 비대칭 키 sign
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new FileSystemResource("src/main/resources/oauth2jwt.jks"), "oauth2jwtpass".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth2jwt"));
        return converter;
    }
}
