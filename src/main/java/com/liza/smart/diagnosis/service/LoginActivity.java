package com.liza.smart.diagnosis.service;

import com.liza.smart.diagnosis.repository.LoginActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginActivity {
    private final LoginActivityRepository loginActivityRepository;

    public void save(com.liza.smart.diagnosis.entity.LoginActivity loginActivity){
        loginActivityRepository.save(loginActivity);
    }


}
