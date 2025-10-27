package com.vukasin.restaurant.security;

import com.vukasin.restaurant.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenHelper jwtTokenHelper;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtTokenHelper jwtTokenHelper, UserService userService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username;
        String token = jwtTokenHelper.getToken(request);

        if (token != null) {
            username = jwtTokenHelper.getUsernameFromToken(token);
            if (username != null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (jwtTokenHelper.validateToken(token, userDetails)) {

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //                    AuthenticationToken auth = new AuthenticationToken(userDetails);
//                    auth.setToken(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("-------------");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
