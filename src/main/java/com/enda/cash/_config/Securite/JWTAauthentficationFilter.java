package com.enda.cash._config.Securite;

import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAauthentficationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAauthentficationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, InternalAuthenticationServiceException {

        AppUser users = null;
        try {
            users = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("*******************");
        System.out.println(users.getUsername());
        System.out.println(users.getPassword());
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User SpringUser = (User) authResult.getPrincipal();

        String JWT = Jwts.builder()
                .setSubject(SpringUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + ConstantSecurity.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, ConstantSecurity.SECRET)
                .claim("roles", SpringUser.getAuthorities())
                .compact();
        response.addHeader(ConstantSecurity.HEADER_STRING, JWT);
        System.out.println(response.getHeader(ConstantSecurity.HEADER_STRING));

    }
}
