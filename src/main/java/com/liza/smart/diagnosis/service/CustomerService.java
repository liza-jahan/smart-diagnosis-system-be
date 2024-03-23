package com.liza.smart.diagnosis.service;

import com.liza.smart.diagnosis.dto.CustomerCreateRequest;
import com.liza.smart.diagnosis.entity.Authority;
import com.liza.smart.diagnosis.entity.Customer;
import com.liza.smart.diagnosis.enums.Role;
import com.liza.smart.diagnosis.repository.AuthorityRepository;
import com.liza.smart.diagnosis.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class CustomerService {


    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final AuthorityRepository authorityRepository;

    public Customer create(CustomerCreateRequest request){

      if(customerRepository.findByEmail(request.getEmail()).isPresent()){
          throw new RuntimeException("extistingCustomer");
      }
        Authority authority = new Authority();
        authority.setName(Role.USER.name());
        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setName(request.getName());
        customer.setMobileNumber(request.getPhoneNumber());
        customer.setPwd(passwordEncoder.encode(request.getPassword()));
       // customer.setAuthorities(Set.of(authority));
        customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
        customer = customerRepository.save(customer);
        authority.setCustomer(customer);
        authorityRepository.save(authority);
        return customer;
    }
}
