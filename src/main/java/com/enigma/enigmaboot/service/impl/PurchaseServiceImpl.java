package com.enigma.enigmaboot.service.impl;

import com.enigma.enigmaboot.constant.ResponseMessage;
import com.enigma.enigmaboot.entity.Customer;
import com.enigma.enigmaboot.entity.Product;
import com.enigma.enigmaboot.entity.Purchase;
import com.enigma.enigmaboot.entity.PurchaseDetail;
import com.enigma.enigmaboot.exception.DataNotFoundException;
import com.enigma.enigmaboot.repository.PurchaseRepository;
import com.enigma.enigmaboot.service.CustomerService;
import com.enigma.enigmaboot.service.ProductService;
import com.enigma.enigmaboot.service.PurchaseDetailService;
import com.enigma.enigmaboot.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    PurchaseDetailService purchaseDetailService;

    @Autowired
    RestTemplate restTemplate;

    @Override
    @Transactional
    public Purchase transaction(Purchase purchase) {
        Purchase purchase1 = purchaseRepository.save(purchase);

        BigDecimal bigDecimal = new BigDecimal("0.00");
        for (PurchaseDetail purchaseDetail : purchase.getPurchaseDetails()) {
            purchaseDetail.setPurchase(purchase1);

            Product product = productService.getProductById(purchaseDetail.getProduct().getId());

            if(product.getStock() < purchaseDetail.getQuantity()) {
                String message = String.format(ResponseMessage.STOCK, "product");
                throw new DataNotFoundException(message);
            }

            product.setStock(product.getStock() - purchaseDetail.getQuantity());
            purchaseDetail.setPriceSell(Double.valueOf(product.getProductPrice()));

            double subTotal = product.getProductPrice().doubleValue() * purchaseDetail.getQuantity();
            System.out.println("SUB TOTAL A : " + subTotal);
            bigDecimal = bigDecimal.add(new BigDecimal(subTotal));
            System.out.println("SUB TOTAL B : " + bigDecimal);

            /*
            Mencari Grand Total
             */
//            double grandTotal = purchaseDetail.getPriceSell() + purchaseDetail.getPriceSell();
//            bigDecimal = bigDecimal.add(new BigDecimal(grandTotal));
//            System.out.println("GRAND TOTAL " + bigDecimal);

            purchaseDetailService.savePurchaseDetail(purchaseDetail);
        }

        Customer customer = customerService.getCustomerById(purchase.getCustomer().getId());
        String url = "http://localhost:8070/wallets/debit";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("phoneNumber", customer.getPhoneNumber()).queryParam("amount", bigDecimal);
        restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, String.class);
        return purchase;
    }
}
