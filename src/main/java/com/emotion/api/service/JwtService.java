package com.emotion.api.service;

import com.emotion.api.utility.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.Map;
import io.micrometer.core.instrument.util.IOUtils;

@Slf4j
@Service
@AllArgsConstructor
public class JwtService {
    public Map<String, Object> getJwtInfo() throws IOException, GeneralSecurityException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        Jws<Claims> claims = null;
        Resource resource = new ClassPathResource("publicKey.pem");
        String p = IOUtils.toString(resource.getInputStream());
        PublicKey publicKey = JwtUtil.getPublicKeyFromString(p);
        try {
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwt.split(" ")[1]);
        } catch (Exception e) {
            log.error("", e);
            throw new UserPrincipalNotFoundException("");
        }
        return (Map<String, Object>)claims.getBody();
    }
}
