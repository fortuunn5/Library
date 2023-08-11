package com.example.Library.config;

import com.example.Library.model.Reader;
import com.example.Library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x -> x
                                .requestMatchers( "/register").permitAll()
                                .requestMatchers("/logbooks/user").hasRole("USER")
                                .requestMatchers("/**").hasRole("ADMIN")

                ).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService u) {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(u);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public UserDetailsService userDetailsService(ReaderRepository readerRepository) {
        return username -> {
            return readerRepository.findByUsername(username).orElseThrow();
            //return reader;

            //throw new UsernameNotFoundException("Reader ‘" + username + "’ not found");
        };
    }
}
