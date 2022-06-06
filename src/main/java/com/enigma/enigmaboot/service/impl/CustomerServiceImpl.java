package com.enigma.enigmaboot.service.impl;

import com.enigma.enigmaboot.constant.ResponseMessage;
import com.enigma.enigmaboot.dto.CustomerSearchDTO;
import com.enigma.enigmaboot.entity.Customer;
import com.enigma.enigmaboot.exception.DataNotFoundException;
import com.enigma.enigmaboot.repository.CustomerRepository;
import com.enigma.enigmaboot.service.CustomerService;
import com.enigma.enigmaboot.specification.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(String customerId) {
        if (!customerRepository.findById(customerId).isPresent()) {
            String message = String.format(ResponseMessage.DATA_NOT_FOUND, "customer", customerId);
            throw new DataNotFoundException(message);
        }
        return customerRepository.findById(customerId).get();
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public Page<Customer> getCustomerPerPage(Pageable pageable, CustomerSearchDTO customerSearchDTO) {
        Specification<Customer> customerSpecification = CustomerSpecification.getSpecification(customerSearchDTO);
        return customerRepository.findAll(customerSpecification, pageable);
    }

    //JPA Query
    @Override
    public List<Customer> getStatus(String status) {
        return customerRepository.findByStatus(status);
    }

    @Override
    public void updateStatus(String id) {
        customerRepository.updateStatus(id);
    }

    public Boolean userNameExist(String username) {
        return customerRepository.existsByUserName(username);
    }
}
