package com.vukasin.restaurant.controller;

import com.vukasin.restaurant.model.User;
import com.vukasin.restaurant.security.AuthenticationRequest;
import com.vukasin.restaurant.security.JwtTokenHelper;
import com.vukasin.restaurant.security.UserAuthenticationToken;
import com.vukasin.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authenticate")
public class AuthenticationController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenHelper.generateToken((User) authentication.getPrincipal());
            long expiresIn = jwtTokenHelper.getExpirationDate(token);

            return ResponseEntity.ok(new UserAuthenticationToken(token, expiresIn));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {

        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("Logged out successfully.");
        }


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session.");

    }



}
