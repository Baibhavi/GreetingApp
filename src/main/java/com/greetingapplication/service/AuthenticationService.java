package com.greetingapplication.service;

import com.greetingapplication.dto.AuthUserDTO;
import com.greetingapplication.dto.LoginDTO;
import com.greetingapplication.exception.UserException;
import com.greetingapplication.model.AuthUser;
import com.greetingapplication.repository.AuthUserRepository;
import com.greetingapplication.util.EmailSenderService;
import com.greetingapplication.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    JwtToken tokenUtil;

    @Autowired
    EmailSenderService emailSenderService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthUser register(AuthUserDTO userDTO) throws Exception{
        AuthUser user=new AuthUser(userDTO);
        System.out.println(user);
        String token=tokenUtil.createToken(user.getUserId());
        authUserRepository.save(user);
        emailSenderService.sendEmail(user.getEmail(),"Registered in Greeting App", "Hii...."
                +user.getFirstName()+"\n You have been successfully registered!\n\n Your registered details are:\n\n User Id:  "
                +user.getUserId()+"\n First Name:  "
                +user.getFirstName()+"\n Last Name:  "
                +user.getLastName()+"\n Email:  "
                +user.getEmail()+"\n Address:  "
                +"\n Token:  " +token);
        return user;
    }

    @Override
    public String login(LoginDTO loginDTO){
        Optional<AuthUser> user= Optional.ofNullable(authUserRepository.findByEmail(loginDTO.getEmail()));
        if (user.isPresent() && user.get().getPassword().equals(loginDTO.getPassword()) ){
            emailSenderService.sendEmail(user.get().getEmail(),"Logged in Successfully!", "Hii...."+user.get().getFirstName()+"\n\n You have successfully logged in into Greeting App!");
            return "Congratulations!! You have logged in successfully!";
        }else {
            throw new UserException("Sorry! Email or Password is incorrect!");
        }
    }
}
