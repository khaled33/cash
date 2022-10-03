package com.enda.cash._config.Securite;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTAuthorrizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        if (httpServletRequest.getMethod().equals("OPTIONS")) {

            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
            httpServletResponse.addHeader("Access-Control-Expose-Headers", "*");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                String jwt = httpServletRequest.getHeader(ConstantSecurity.HEADER_STRING);
                if (jwt == null) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }

                Claims clims = Jwts.parser()
                        .setSigningKey(ConstantSecurity.SECRET)

                        .parseClaimsJws(jwt)
                        .getBody();
                String username = clims.getSubject();


                ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) clims.get("roles");
                Collection<GrantedAuthority> authorities = new ArrayList<>();

                roles.forEach(r -> {
                    authorities.add(new SimpleGrantedAuthority(r.get("authority")));
                });


                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (ExpiredJwtException e) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

    }
}
