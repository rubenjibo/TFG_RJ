package com.RubenJimenez.TFG;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.http.HttpMethod;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager usersdetais() throws Exception{
        List<UserDetails> users=List.of(

                User
                        .withUsername("admin")
                        .password("{noop}admin1234")
                        .roles("ADMIN")
                        .build());
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(cus->cus.disable())
            .authorizeHttpRequests(aut->
                aut.requestMatchers(HttpMethod.POST,"/product/insert").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/product/delete").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/product/update").hasRole("ADMIN")
                //.requestMatchers(HttpMethod.POST,"/event/insert").hasRole("ADMIN")
                //.requestMatchers(HttpMethod.POST,"/event/delete").hasRole("ADMIN")
                //.requestMatchers(HttpMethod.GET,"/product/findAll").hasRole("ADMIN")
                .anyRequest().permitAll()
            ).httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
