package org.cn.gatewayservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final RSAConfig rsaConfig;

    public SecurityConfig(RSAConfig rsaConfig) {
        this.rsaConfig = rsaConfig;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable() // Disable CSRF as it's stateless
                .authorizeExchange()
                .pathMatchers("/USER-SERVICE/login").permitAll()
                .pathMatchers("/USER-SERVICE/api/chercheurs/{id}").permitAll()
                .pathMatchers("/SEARCH-SERVICE/api/projects/**").hasAuthority("ENSEIGNANT")
                .pathMatchers("/USER-SERVICE/api/enseignant/**").hasAuthority("ENSEIGNANT")

                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt(); // Enable OAuth2 Resource Server support

        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        // Ensure that rsaConfig.publicKey() returns a valid PublicKey
        return NimbusReactiveJwtDecoder.withPublicKey(rsaConfig.publicKey()).build();
    }
}
