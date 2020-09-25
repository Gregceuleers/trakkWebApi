package com.trakknamur.demo.configs;

import com.trakknamur.demo.services.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Chargement des utilisateurs depuis la DB

        auth.userDetailsService(this.userDetailsService);

        // Chargement en mémoire d'utilisateurs hardcodés

//        PasswordEncoder encoder =
//                PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//        auth
//                .inMemoryAuthentication()
//                .withUser("greg")
//                .password(encoder.encode("1234"))
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password(encoder.encode("1234"))
//                .roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST,
//                        "/parcours/**",
//                        "/trous/**",
//                        "/users/**"
//                ).hasAuthority("ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // Pour accéder et savoir afficher la page de console H2
                .and()
                .headers().frameOptions().disable();
    }
}
