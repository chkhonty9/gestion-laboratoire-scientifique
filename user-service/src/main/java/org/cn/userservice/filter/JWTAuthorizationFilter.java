package org.cn.userservice.filter;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Request-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, X-Auth-Token, authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
        //response.setHeader("Access-Control-Allow-Credentials", "true");

        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
        } else {

            String jwtToken = request.getHeader("Authorization");
            //System.out.println("Token : " + jwtToken);
            if(jwtToken == null || !jwtToken.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }
            jwtToken = jwtToken.substring("Bearer ".length());

            System.out.println("Token : " + jwtToken);

            Jwt jwtDecoded = jwtDecoder.decode(jwtToken);
            String email = jwtDecoded.getSubject();
            System.out.println("email : " + email);
            List<String> role = (List<String>) jwtDecoded.getClaims().get("role");
            System.out.println("role : " + role);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            role.forEach(r -> {
                authorities.add(new SimpleGrantedAuthority(r));
            });
            UsernamePasswordAuthenticationToken user =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(user);
            filterChain.doFilter(request, response);

        }

    }

}

