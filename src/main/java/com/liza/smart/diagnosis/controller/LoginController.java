package com.liza.smart.diagnosis.controller;


import com.liza.smart.diagnosis.dto.CustomerCreateRequest;
import com.liza.smart.diagnosis.entity.Customer;
import com.liza.smart.diagnosis.repository.CustomerRepository;
import com.liza.smart.diagnosis.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerUser(@RequestBody CustomerCreateRequest customer) {
        ResponseEntity response = null;
        try {
            Customer savedCustomer = customerService.create(customer);
            if (savedCustomer.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(savedCustomer);
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        return customerRepository.findByEmail(authentication.getName()).orElse(null);

    }
    
}
