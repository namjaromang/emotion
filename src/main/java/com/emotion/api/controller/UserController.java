package com.emotion.api.controller;


import com.emotion.api.entity.UserEntity;
import com.emotion.api.payload.request.SignUpRequest;
import com.emotion.api.payload.response.ApiResponse;
import com.emotion.api.service.JwtService;
import com.emotion.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;


    /**
     * 회원가입 api
     *
     * @param signUpRequest
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) throws IOException, GeneralSecurityException {
        //이메일 중복처리
        if (userService.overlapEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse("1001", "duplicated EMAIL "), HttpStatus.ACCEPTED);
        }
        UserEntity u = userService.singup(signUpRequest);

        return new ResponseEntity(new ApiResponse("200", "registered successfully"), HttpStatus.ACCEPTED);
    }

    public boolean overlapEmail(String email) {
        Boolean emailOverlap = userService.overlapEmail(email);
        return emailOverlap;
    }

    /**
     * 비밀번호확인 API
     *
     * @param password
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @PostMapping("/confirmPassword")
    public ResponseEntity<ApiResponse> confirmPassword(@Valid @RequestBody String password) throws IOException, GeneralSecurityException {
        userService.confirmPassword(password);
        return new ResponseEntity(new ApiResponse("200", "confirm password"), HttpStatus.ACCEPTED);
    }

    /**
     * 비밀번호변경 API
     *
     * @param password
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @PostMapping("/changePassword")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody String password) throws IOException, GeneralSecurityException {
        userService.changePassword(password);
        return new ResponseEntity(new ApiResponse("200", "change password"), HttpStatus.ACCEPTED);
    }

    /**
     * 탈퇴 API
     *
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdraw() throws IOException, GeneralSecurityException {
        userService.withdraw();
        return new ResponseEntity(new ApiResponse("200", "You have been withdrawn."), HttpStatus.ACCEPTED);
    }
}
