package com.liza.smart.diagnosis.configuration;

import com.liza.smart.diagnosis.filter.AutheticationSuccessFilter;
import com.liza.smart.diagnosis.filter.AuthorityLoginAfterFilter;
import com.liza.smart.diagnosis.filter.AuthurityLoginAtFilter;
import com.liza.smart.diagnosis.filter.CsrfCookieFilter;
import com.liza.smart.diagnosis.filter.JwtTokenGenerationFilter;
import com.liza.smart.diagnosis.filter.JwtTokenValidatorFilter;
import com.liza.smart.diagnosis.filter.RequestValidationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor

public class ApplicationSecurityConfig {

    private final AutheticationSuccessFilter autheticationSuccessFilter;
    private final AuthurityLoginAtFilter authurityLoginAtFilter;
    private final AuthorityLoginAfterFilter authorityLoginAfterFilter;
    private final RequestValidationFilter requestValidationFilter;
    private final CsrfCookieFilter csrfCookieFilter;

    @Value("${application.server.origin}")
    public String origin;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        CsrfTokenRequestAttributeHandler attributeHandler = new CsrfTokenRequestAttributeHandler();
//        attributeHandler.setCsrfRequestAttributeName("_csrf");

        return http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Collections.singletonList(origin));
                    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfiguration.setExposedHeaders(List.of("Authorization"));
                    corsConfiguration.setMaxAge(3600L);
                    return corsConfiguration;
                }))
          .csrf().disable()
//                .csrf(csrf ->
//                        csrf.csrfTokenRequestHandler(attributeHandler).ignoringRequestMatchers("/contact", "/register")
//                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .addFilterBefore(requestValidationFilter, BasicAuthenticationFilter.class)
//                .addFilterAt(authurityLoginAtFilter, BasicAuthenticationFilter.class)
//                .addFilterAfter(authorityLoginAfterFilter, BasicAuthenticationFilter.class)
//                .addFilterAfter(autheticationSuccessFilter, BasicAuthenticationFilter.class)
//                .addFilterAfter(csrfCookieFilter, BasicAuthenticationFilter.class)
//                .addFilterAfter(new JwtTokenGenerationFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)

                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/myAccount", "/myCards", "/myLoans").hasRole("USER")
                        .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("contact", "notices","diagnosis","doctor","doctors").permitAll()
                       .requestMatchers(POST, "/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults()).build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
