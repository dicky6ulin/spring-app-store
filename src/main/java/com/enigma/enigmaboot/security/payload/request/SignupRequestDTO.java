package com.enigma.enigmaboot.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.Set;

@Getter
@Setter
public class SignupRequestDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private Date dateOfBirth;
    private String status;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    private String phoneNumber;
    private Set<String> role;
}
