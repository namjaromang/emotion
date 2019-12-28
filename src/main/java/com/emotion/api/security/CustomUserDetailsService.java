package com.emotion.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleTypeRepository roleTypeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findTopByEmailOrderByEmailVerifiedDesc(username).orElseThrow(() -> new UsernameNotFoundException("user is not exists"));
        if (userEntity == null) throw new UsernameNotFoundException("Invalid username or password.");
        List<RoleTypeEntity> roleList = roleTypeRepository.findByRoleTypeUserRoleRelEntityUserId(userEntity.getUserId());
        List<GrantedAuthority> authority = roleList.stream().map(r -> new SimpleGrantedAuthority(r.getRoleEngName())).collect(Collectors.toList());
        return new User(username, userEntity.getPassword(), authority);
    }
}
