package com.example.taskManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //encrypting password
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //users with roles
    @Bean
    public UserDetailsService getUserDetailsService()
    {
        //step 1
        return new CustomUserDetailsService();
    }

    //authentication for APIs
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeHttpRequests()

                .requestMatchers("/user/**")
                .permitAll()

                .requestMatchers("/task/**")
                .hasAnyRole("EMPLOYEE","ADMIN")

                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());

        return daoAuthenticationProvider;
    }
}
