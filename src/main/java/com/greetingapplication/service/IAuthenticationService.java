package com.greetingapplication.service;

import com.greetingapplication.dto.AuthUserDTO;
import com.greetingapplication.dto.LoginDTO;
import com.greetingapplication.model.AuthUser;

public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;
    String login(LoginDTO loginDTO);
    String forgotPassword(String email, String newPassword);

}
