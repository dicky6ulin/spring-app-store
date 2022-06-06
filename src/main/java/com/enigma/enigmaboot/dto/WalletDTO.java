package com.enigma.enigmaboot.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WalletDTO {
    private String id;
    private String phoneNumber;
    private BigDecimal balance;
}
