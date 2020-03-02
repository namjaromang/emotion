package com.emotion.api.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {
    @Email
    private String email;
    @Size(min = 6, max = 20)
    private String password;
    @Size(min = 2, max = 20)
    private String username;
    //마케팅 수신동의
    private boolean marketingAgreement = true;
}
