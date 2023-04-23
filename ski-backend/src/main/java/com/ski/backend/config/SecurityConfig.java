package com.ski.backend.config;

import com.ski.backend.config.jwt.JwtAuthenticationFilter;
import com.ski.backend.config.jwt.JwtAuthorizationFilter;
import com.ski.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final CorsConfig corsConfig;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl())
                .and()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/api/resort/**")
                        .access("hasRole('ROLE_ADMIN')")
                        .antMatchers("/api/board/**", "/api/club/**", "/api/carpool/**", "/api/chat/**", "/api/club/**", "/api/clubBoard/**", "/api/board/comment/**", "/api/submit/**", "/api/tayo/**")
                        .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                        .antMatchers("/api/bookmark/**")
                        .access("hasRole('ROLE_USER') or hasRole('ROLE_GUEST') or hasRole('ROLE_ADMIN')")
                        .antMatchers("/api/user/join").permitAll()
                        .anyRequest().permitAll())
                .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }

}
