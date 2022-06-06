package com.enigma.enigmaboot.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String type = "Bearer";
    private String id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String status;
    private String userName;
    private String password;
    private String phoneNumber;
    private List<String> roles;

    public LoginResponseDTO(String token, String id, String firstName, String lastName, Date dateOfBirth, String status, String username, String phoneNumber , List<String> roles) {
        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.userName = username;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}