package com.greetingapplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPassword {
    @NotBlank(message = "Password Cannot be Empty")
    private String password;
}
