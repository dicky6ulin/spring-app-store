package com.enigma.enigmaboot.service.impl;

import com.enigma.enigmaboot.entity.PurchaseDetail;
import com.enigma.enigmaboot.repository.PurchaseDetailRepository;
import com.enigma.enigmaboot.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService {

    @Autowired
    PurchaseDetailRepository purchaseDetailRepository;

    @Override
    public PurchaseDetail savePurchaseDetail(PurchaseDetail purchaseDetail) {
        return purchaseDetailRepository.save(purchaseDetail);
    }
}
