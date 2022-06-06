package com.enigma.enigmaboot.controller;

import com.enigma.enigmaboot.constant.ApiURLConstant;
import com.enigma.enigmaboot.constant.ResponseMessage;
import com.enigma.enigmaboot.dto.WalletDTO;
import com.enigma.enigmaboot.utils.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping(ApiURLConstant.OPO)
public class WalletRestTemplate {

    @Autowired
    RestTemplate restTemplate;

//    @PostMapping
//    public ResponseEntity<WalletDTO> createWallet (@RequestBody WalletDTO walletDTO) {
//        String url = "http://localhost:8070/wallets";
//        ResponseEntity<WalletDTO> response = restTemplate.postForEntity(URI.create(url),walletDTO,WalletDTO.class);
//        return response;
//    }
    @PostMapping
    public ResponseEntity<Response<WalletDTO>> createWallet (@RequestBody WalletDTO walletDTO) {
        String url = "http://localhost:8070/wallets";

        ResponseEntity<Response> walletDTO1 = restTemplate.postForEntity(URI.create(url), walletDTO, Response.class);

        System.out.println("WALLET DTO 1 : " + walletDTO1);

        Object walletDTO2 = walletDTO1.getBody().getData();
        WalletDTO walletDTO3 = walletDTO(walletDTO2);

        Response<WalletDTO> response = new Response<>();
        String message = String.format(ResponseMessage.DATA_INSERT, "wallet");
        response.setMessage(message);
        response.setData(walletDTO3);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    public WalletDTO walletDTO (Object obj) {
        try {
            return new ObjectMapper().convertValue(obj, WalletDTO.class);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
