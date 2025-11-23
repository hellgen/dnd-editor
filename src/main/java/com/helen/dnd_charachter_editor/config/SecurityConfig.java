package com.helen.dnd_charachter_editor.config;

import com.helen.dnd_charachter_editor.filter.JwtFilter;
import com.helen.dnd_charachter_editor.handler.CustomAccessDeniedHandler;
import com.helen.dnd_charachter_editor.handler.CustomLogoutHandler;
import com.helen.dnd_charachter_editor.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomLogoutHandler customLogoutHandler;
//    private final AuthService userDetailsService;
    //тест рестарта пода

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/register",
                                "/auth/login",
                                "/auth/refresh"
                        ).permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
//                .userDetailsService(userDetailsService)

                //fsdfsdfsdfsdfsdfsdfs

                .exceptionHandling(e -> e
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(log -> log
                        .logoutUrl("/logout")
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessHandler((req, res, auth) -> {
                            SecurityContextHolder.clearContext();
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
