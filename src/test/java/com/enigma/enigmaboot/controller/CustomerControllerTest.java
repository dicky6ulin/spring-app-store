package com.enigma.enigmaboot.controller;

import com.enigma.enigmaboot.entity.Customer;
import com.enigma.enigmaboot.entity.Product;
import com.enigma.enigmaboot.service.CustomerService;
import com.enigma.enigmaboot.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void addCustomer() {
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(new Customer("a111", "Dicky", "Setiawan", Date.valueOf("2020-10-10"), "Swasta","dicky123", "root"));
        Customer customer = new Customer("a111", "Dicky", "Setiawan", Date.valueOf("2020-10-10"), "Swasta","dicky123", "root");
//        Customer customer1 = customerController.addCustomer(customer);
        ResponseEntity<Response<Customer>> customer1 = customerController.addCustomer(customer);
//        assertThat(customer1.getFirstName()).isEqualTo("Dicky"); // non message
        assertThat(customer1.getBody().getData().getFirstName()).isEqualTo("Dicky");
    }

    @Test
    void createProductWithResponseHeader() throws Exception {
        given(customerService.saveCustomer(any(Customer.class))).willAnswer(invocationOnMock -> {
            System.out.println("INVOKE : " + invocationOnMock);
            return invocationOnMock.getArgument(0);
        });

        Customer customer = new Customer("a111", "Dicky", "Setiawan", Date.valueOf("2020-10-10"), "Swasta","dicky123", "root");
        this.mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(customer)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void createCustomerWithHeaderStatus400() throws Exception {
        this.mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is4xxClientError());
    }

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Test
    void getCustomerById() {
    }

    @Test
    void getCustomerPerPage() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }
}