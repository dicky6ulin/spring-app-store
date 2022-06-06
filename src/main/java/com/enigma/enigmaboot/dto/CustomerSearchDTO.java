package com.enigma.enigmaboot.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerSearchDTO {
    private String searchCustomerId;
    private String searchCustomerFirstName;
    private String searchCustomerLastName;
    private Date searchCustomerDateOfBirth;
    private String searchCustomerStatus;
    private String searchCustomerUserName;
}