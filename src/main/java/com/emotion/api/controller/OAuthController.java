package com.emotion.api.controller;

import com.emotion.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Controller
public class OAuthController {

    @Autowired
    UserService userService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping({"/user", "/me"})
    @ResponseBody
    public Map<String, Object> userInfo() throws IOException, GeneralSecurityException {
        return userService.getUserInfo();
    }

}