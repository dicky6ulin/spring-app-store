package com.enigma.enigmaboot.controller;

import com.enigma.enigmaboot.constant.ERole;
import com.enigma.enigmaboot.entity.Customer;
import com.enigma.enigmaboot.entity.Role;
import com.enigma.enigmaboot.repository.RoleRepository;
import com.enigma.enigmaboot.security.jwt.JwtUtils;
import com.enigma.enigmaboot.security.payload.request.LoginRequestDTO;
import com.enigma.enigmaboot.security.payload.request.SignupRequestDTO;
import com.enigma.enigmaboot.security.payload.response.LoginResponseDTO;
import com.enigma.enigmaboot.security.payload.response.MessageResponse;
import com.enigma.enigmaboot.dto.CustomerDetailsImplDTO;
import com.enigma.enigmaboot.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    RoleRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signupRequestDTO){

//        System.out.println("SIGNUP REQUEST : " + signupRequestDTO);

        if(customerService.userNameExist(signupRequestDTO.getUserName())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken"));
        }

        Customer customer = new Customer(
                signupRequestDTO.getFirstName(),
                signupRequestDTO.getLastName(),
                signupRequestDTO.getDateOfBirth(),
                signupRequestDTO.getStatus(),
                signupRequestDTO.getUserName(),
                passwordEncoder.encode(signupRequestDTO.getPassword()),
                signupRequestDTO.getPhoneNumber()
        );
        Set<String> strRoles = signupRequestDTO.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){
            Role userRole = repository.findRoleByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role){
                    case "admin":
                        Role adminRole = repository.findRoleByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = repository.findRoleByName(ERole.ROLE_USER)
                                .orElseThrow(() ->  new RuntimeException("Error: Role is not found"));
                        roles.add(userRole);
                }
            });
        }
        customer.setRoles(roles);
        customerService.saveCustomer(customer);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(), loginRequestDTO.getUserPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomerDetailsImplDTO customerDetails = (CustomerDetailsImplDTO) authentication.getPrincipal();
        List<String> roles = customerDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new LoginResponseDTO(jwt, customerDetails.getId(),
                customerDetails.getFirstName(), customerDetails.getLastName(), (Date) customerDetails.getDateOfBirth(),
                customerDetails.getStatus(), customerDetails.getUsername(), customerDetails.getPhoneNumber(), roles));
    }
}
