package com.enigma.enigmaboot.service.impl;

import com.enigma.enigmaboot.entity.Customer;
import com.enigma.enigmaboot.entity.Product;
import com.enigma.enigmaboot.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerServiceImpl;

    @Test
    void saveCustomer() {
        Customer customer = new Customer("a111", "Dicky", "Setiawan", Date.valueOf("2020-10-10"), "Swasta","dicky123", "root");
        customerServiceImpl.saveCustomer(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void saveCustomerSuccess() {
        when(customerRepository.save(new Customer("a111", "Dicky", "Setiawan", Date.valueOf("2020-10-10"), "Swasta","dicky123", "root"))).thenReturn(new Customer("a111", "Dicky", "Setiawan", Date.valueOf("2020-10-10"), "Swasta","dicky123", "root"));
        Customer customer = new Customer("a111", "Dicky", "Setiawan", Date.valueOf("2020-10-10"), "Swasta","dicky123", "root");
        Customer customer1 = customerServiceImpl.saveCustomer(customer);
        assertEquals("Dicky", customer1.getFirstName());
    }

    @Test
    void getCustomerById() {
        when(customerRepository.findById("a111")).thenReturn(Optional.of(Optional.of(new Customer("a111", "Dicky", "Setiawan", Date.valueOf("2020-10-10"), "Swasta", "dicky123", "root")).get()));
        Customer customer = customerServiceImpl.getCustomerById("a111");
        assertEquals("Dicky", customer.getFirstName());
        assertEquals("Setiawan", customer.getLastName());
    }

    @Test
    void deleteCustomer() {
        customerServiceImpl.deleteCustomer("a111");
        verify(customerRepository, times(1)).deleteById("a111");
    }

    @Test
    void getCustomerPerPage() {
    }
}