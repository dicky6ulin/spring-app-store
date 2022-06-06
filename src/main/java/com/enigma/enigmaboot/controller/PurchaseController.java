package com.enigma.enigmaboot.controller;

import com.enigma.enigmaboot.constant.ApiURLConstant;
import com.enigma.enigmaboot.entity.Purchase;
import com.enigma.enigmaboot.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiURLConstant.PURCHASE)
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping
    public Purchase transaction (@RequestBody Purchase purchase) {
        return purchaseService.transaction(purchase);
    }
}
