package com.service.algorithm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    // обязательно для работы, иначе ошибка java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/registration").permitAll()
                        .requestMatchers("/convert").hasRole("USER") // в БД нужно указывать как "ROLE_USER"
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/convert", true)
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

//    для работы JdbcUserDetailsManager без создания новых запросов необходимы таблицы с полями, как в этих запросах
//    либо использовать подключения через Hibernate (чем я и воспользовался)
//    CREATE TABLE users (
//            id SERIAL PRIMARY KEY,
//            username varchar(45) NOT NULL,
//            password varchar(45) NOT NULL,
//            enabled boolean NOT NULL DEFAULT TRUE
//    );
//
//    CREATE TABLE authorities (
//            id SERIAL PRIMARY KEY,
//            username varchar(45) NOT NULL,
//            authority varchar(45) NOT NULL
//    );
    @Bean
    public UserDetailsService userDetailsService() {
        return new JdbcUserDetailsManager(dataSource);
    }

}