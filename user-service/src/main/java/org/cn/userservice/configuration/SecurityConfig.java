package org.cn.userservice.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;

import org.cn.userservice.filter.JWTAuthorizationFilter;
import org.cn.userservice.filter.JwtAuthenticationFilter;
import org.cn.userservice.service.impl.UserDetailService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableConfigurationProperties
@AllArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final RSAConfig rsaConfig;
    private final UserDetailService userDetailsService;


    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return new ProviderManager(daoAuthenticationProvider);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtEncoder());
        JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(jwtDecoder());
        return http
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(c -> c.disable())
                .authorizeRequests(auth -> auth
                        .antMatchers("/login/**").permitAll()
                        .antMatchers("/token/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/chercheurs/{id}").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/enseignants/{id}").permitAll()
                        .antMatchers(HttpMethod.PUT,"/api/chercheurs").hasAuthority("CHERCHEUR")
                        .antMatchers("/api/enseignants/**").hasAuthority("ENSEIGNANT")
                        .antMatchers("/api/chercheurs/**").hasAuthority("ENSEIGNANT")
                        .anyRequest().authenticated()
                )
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaConfig.publicKey()).privateKey(rsaConfig.privateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaConfig.publicKey()).build();
    }



}
