package com.helen.dnd_charachter_editor.config;

import com.helen.dnd_charachter_editor.filter.JwtFilter;
import com.helen.dnd_charachter_editor.handler.CustomAccessDeniedHandler;
import com.helen.dnd_charachter_editor.handler.CustomLogoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

/**
 * Configuration class for security config.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomLogoutHandler customLogoutHandler;
//    private final AuthService userDetailsService;

    /**
     * Filters chain.
     * @param http value used by this operation
     * @return result of the operation
     * @throws Exception when the operation cannot be completed
     */
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
                        .requestMatchers(
                                HttpMethod.GET,
                                "/abilities",
                                "/abilities/*",
                                "/spells",
                                "/spells/*",
                                "/classes",
                                "/classes/*",
                                "/classes/*/features",
                                "/classes/*/features/*",
                                "/classes/*/spells",
                                "/classes/*/class-archetypes",
                                "/classes/*/class-archetypes/*",
                                "/classes/*/class-archetypes/*/features",
                                "/classes/*/class-archetypes/*/features/*",
                                "/races",
                                "/races/*",
                                "/races/*/subraces",
                                "/races/*/subraces/*",
                                "/races/*/features",
                                "/races/*/features/*",
                                "/races/*/subraces/*/features",
                                "/races/*/subraces/*/features/*"
                        ).permitAll()
                        .requestMatchers("/actuator/**").permitAll()   // <--- обязательно
                        .anyRequest().authenticated()
//                        .anyRequest().permitAll()
                )
//                .userDetailsService(userDetailsService)

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
                        .logoutSuccessHandler((req, res, auth) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }

    /**
     * Executes the password encoder operation.
     * @return result of the operation
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Executes the authentication manager operation.
     * @param config value used by this operation
     * @return result of the operation
     * @throws Exception when the operation cannot be completed
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
