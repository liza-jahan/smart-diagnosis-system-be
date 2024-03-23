package com.liza.smart.diagnosis.filter;

import com.liza.smart.diagnosis.entity.LoginActivity;
import com.liza.smart.diagnosis.repository.LoginActivityRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutheticationSuccessFilter extends OncePerRequestFilter {


    private final LoginActivityRepository loginActivityRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null != authentication){
            LoginActivity loginActivity = new LoginActivity();
            loginActivity.setUserId(authentication.getName());
            loginActivity.setLoginTime(new Date());
            loginActivity.setSessionId(request.getRequestedSessionId());
            loginActivity.setIp(request.getRemoteHost());
            loginActivityRepository.save(loginActivity);
        }

        filterChain.doFilter(request, response);

    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }
}
