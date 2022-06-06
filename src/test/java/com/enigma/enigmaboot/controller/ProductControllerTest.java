package com.enigma.enigmaboot.controller;

import com.enigma.enigmaboot.entity.Product;
import com.enigma.enigmaboot.service.ProductService;
import com.enigma.enigmaboot.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @InjectMocks
    ProductController productController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void addProduct() {
        when(productService.saveProduct(any(Product.class))).thenReturn(new Product("a01","Book",6000,10));
        Product product = new Product("a01","Book",6000,10);
        ResponseEntity<Response<Product>> product1 = productController.addProduct(product);
//        assertThat(product1.getProductName()).isEqualTo("Book"); // not message
        assertThat(product1.getBody().getData().getProductName()).isEqualTo("Book");
    }

    @Test
    void createProductWithResponseHeader() throws Exception {
        given(productService.saveProduct(any(Product.class))).willAnswer(invocationOnMock -> {
            System.out.println("INVOKE : " + invocationOnMock);
            return invocationOnMock.getArgument(0);
        });

        Product product = new Product("001","shanpo",5000,10);
        this.mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(product)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("data.productName", Matchers.is(product.getProductName()))); //isOk or isCreated
    }

    @Test
    void createProductWithHeaderStatus400() throws Exception {
        this.mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is4xxClientError());
    }

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Test
    void getProductPerPage() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}