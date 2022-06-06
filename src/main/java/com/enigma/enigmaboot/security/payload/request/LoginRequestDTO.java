package com.enigma.enigmaboot.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequestDTO {
    @NotBlank
    private String userName;
    @NotBlank
    private String userPassword;
}
