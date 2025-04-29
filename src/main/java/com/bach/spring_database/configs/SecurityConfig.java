package com.bach.spring_database.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PERMIT_ALL = {
            "/",
            "/api/v1/register",
            "/api/v1/verifyEmail",
            "/api/v1/otp",
            "/api/v1/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/api/v1/logout",
    };

    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(PERMIT_ALL).permitAll()
                        .anyRequest().authenticated());

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint));

//        http.oauth2ResourceServer(oauth2 ->
//                oauth2.jwt(jwtConfigurer ->
//                        jwtConfigurer
//                                .jwtAuthenticationConverter(jwtConverter())));
        return http.build();

    }

    @Bean
    JwtAuthenticationConverter jwtConverter() {
        return new JwtAuthenticationConverter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
