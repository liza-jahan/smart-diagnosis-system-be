package com.liza.smart.diagnosis.configuration;

import com.liza.smart.diagnosis.entity.Authority;
import com.liza.smart.diagnosis.entity.Customer;
import com.liza.smart.diagnosis.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ApplicationUsernamePasswordAuthProvider implements AuthenticationProvider {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username =  authentication.getName();
        String password =  authentication.getCredentials().toString();

        Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid username"));

        if (passwordEncoder.matches(password, customer.getPwd())){
            return new UsernamePasswordAuthenticationToken(username,
                    password, getAuthorities(customer.getAuthorities()));
        }
        throw new BadCredentialsException("Password does not matches");

    }

    private List<GrantedAuthority> getAuthorities(Set<Authority> authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        authorities.forEach(authority -> grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName())));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
