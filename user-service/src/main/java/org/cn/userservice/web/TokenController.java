package org.cn.userservice.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        System.out.println("::::::::::::::::: Refresh token ::::::::::::::::::::");
        String refreshToken = request.getHeader("Refresh-Token");
        System.out.println("refresh token : " + refreshToken);
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing Refresh Token");
        }

        try {
            // Decode and validate the refresh token
            refreshToken = refreshToken.substring("Bearer ".length());

            Jwt jwt = jwtDecoder.decode(refreshToken);
            String username = jwt.getSubject();

            // Optionally, you can check if the refresh token is still valid (e.g., expiration)
            if (jwt.getExpiresAt().isBefore(Instant.now())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token Expired");
            }

            System.out.println("Username : " + username);

            List<String> roles = (List<String>) jwt.getClaims().get("roles");
            System.out.println("roles : " + roles);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(r -> {
                authorities.add(new SimpleGrantedAuthority(r));
            });

            JwtClaimsSet jwtClaimSet_Access_Token = JwtClaimsSet.builder()
                    .issuer(request.getRequestURI())
                    .subject(username)
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plus(2, ChronoUnit.MINUTES))
                    .claim("username", username)
                    .claim("roles", roles)
                    .build();
            String Access_Token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimSet_Access_Token)).getTokenValue();

            JwtClaimsSet jwtClaimsSet_Refresh_Token = JwtClaimsSet.builder()
                    .issuer(request.getRequestURI())
                    .subject(username)
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                    .claim("username", username)
                    .claim("roles", roles)
                    .build();
            String Refresh_Token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_Refresh_Token)).getTokenValue();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + Access_Token); // Add Access Token
            httpHeaders.add("Refresh-Token", "Bearer " + Refresh_Token); // Add Refresh Token

            // Return the new access token
            return ResponseEntity.ok()
                    .headers(httpHeaders) // Set the headers
                    .body("New Access Token Issued");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }
    }

}
