package com.bikkadIT.ElectronicStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails normal = User.builder()
                .username("MANISH")
                .password(passwordEncoder().encode("manish"))
                .roles("NORMAL")
                .build();

        UserDetails admin = User.builder()
                .username("RUDRA")
                .password(passwordEncoder().encode("rudra"))
                .roles("ADMIN")
                .build();


        //InMemoryUserDetailsManager is implemented class of UserDetailsService

        return new InMemoryUserDetailsManager(normal,admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
