package com.liza.smart.diagnosis.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Component
public class RequestValidationFilter implements Filter {
    public static final String AUTHENTICATION_SCHEME_BASIC= "Basic";
    private final Charset credentialsCharset = StandardCharsets.UTF_8;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String header = servletRequest.getHeader(AUTHORIZATION);

        if (header != null){
            header = header.trim();
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)){
                byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded;
                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                    String token = new String(decoded, credentialsCharset);
                    int delim =  token.indexOf(":");
                    if (delim == -1){
                        throw new BadCredentialsException("Invalid Basic Authentication token");
                    }
                    String email =  token.substring(0, delim);
                    if (email.toLowerCase().contains("test")){
                        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }catch (IllegalArgumentException exception){
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        }
        chain.doFilter(request, response);

    }
}
