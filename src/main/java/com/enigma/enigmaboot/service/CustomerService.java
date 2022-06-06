package com.enigma.enigmaboot.service;

import com.enigma.enigmaboot.dto.CustomerSearchDTO;
import com.enigma.enigmaboot.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    public List<Customer> getAllCustomer();
    public Customer getCustomerById(String customerId);
    void deleteCustomer(String customerId);
    Page<Customer> getCustomerPerPage(Pageable pageable, CustomerSearchDTO customerSearchDTO);

    //JPA Query
    public List<Customer> getStatus (String status);

    public void updateStatus (String id);
}
