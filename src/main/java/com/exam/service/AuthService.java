package com.exam.service;

import com.exam.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public String userLogin(String username, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("auth : " + auth.getAuthorities());

            if (auth.isAuthenticated()) {
                return jwtUtil.generateToken(username);
            }

        }catch(AuthenticationException e ){
                throw new RuntimeException( "Invalid username and password");
        }
        return "Bad credentials";
    }



}
