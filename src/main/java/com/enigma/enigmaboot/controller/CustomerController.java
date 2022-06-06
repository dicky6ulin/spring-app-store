package com.enigma.enigmaboot.controller;

import com.enigma.enigmaboot.constant.ApiURLConstant;
import com.enigma.enigmaboot.constant.ResponseMessage;
import com.enigma.enigmaboot.dto.CustomerSearchDTO;
import com.enigma.enigmaboot.entity.Customer;
import com.enigma.enigmaboot.service.CustomerService;
import com.enigma.enigmaboot.utils.PageResponseWrapper;
import com.enigma.enigmaboot.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiURLConstant.CUSTOMER)
public class CustomerController {

    @Autowired
    CustomerService customerService;

//    @ResponseStatus(HttpStatus.CREATED) //200 OK | 200 Created
//    @PostMapping
//    public Customer addCustomer(@RequestBody Customer customer) {
//        return customerService.saveCustomer(customer);
//    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Response<Customer>> addCustomer(@RequestBody Customer customer) {
        Response<Customer> response = new Response<>();
        String message = String.format(ResponseMessage.DATA_INSERT, "customer");
        response.setMessage(message);
        response.setData(customerService.saveCustomer(customer));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

//    @GetMapping
//    public List<Customer> getAllCustomer() {
//        return customerService.getAllCustomer();
//    }

    //JPA Query
//    @GetMapping
//    public List<Customer> getStatus(String status) {
//        return customerService.getStatus(status);
//    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponseWrapper<Customer> getCustomerPerPage(@RequestBody CustomerSearchDTO customerSearchDTO,
                                                            @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "3") Integer sizePerPage,
                                                            @RequestParam(name = "sortBy", defaultValue = "firstName") String sortBy,
                                                            @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        Sort sorting = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page,sizePerPage,sorting);
        Page<Customer> customerPage = customerService.getCustomerPerPage(pageable, customerSearchDTO);
        return new PageResponseWrapper<>(customerPage);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    //Jpa Query
//    @PutMapping("/{id}")
//    public void updateCustomer(@PathVariable String id) {
//        customerService.updateStatus(id);
//    }

    @DeleteMapping
    public void deleteCustomer(@RequestParam String id) {
        customerService.deleteCustomer(id);
    }
}