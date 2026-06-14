package com.exam.service;

import com.exam.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public Map<String,String> userLogin(String username, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("auth : " + auth.getAuthorities());
            Users userDetails =(Users) auth.getPrincipal();
            System.out.println("userdetail object inside login "+userDetails.toString());
            String token = jwtService.generateToken(userDetails);

            Map<String,String> data =  new HashMap<>();
            data.put("token",token);
            data.put("ROLE", userDetails.getRole().toString());
                return data;

        }catch(AuthenticationException e ){
                throw new BadCredentialsException( "Invalid username and password");
        }
    }



}
