package com.exam.config;



import com.exam.model.Users;
import com.exam.service.CustomUserService;
import com.exam.service.JwtService;
import com.exam.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {


    private JwtService jwtService;


    private CustomUserService customUserService;

    public JwtFilter(JwtService jwtService, CustomUserService customUserService) {
        this.jwtService = jwtService;
        this.customUserService = customUserService;
    }

    @Override
    protected boolean shouldNotFilter(jakarta.servlet.http.HttpServletRequest request) throws jakarta.servlet.ServletException {
        String path = request.getRequestURI();
        // Do not run JWT validation logic if it's the internal error endpoint
        return "/error".equals(path);
    }

    @Override
    protected  void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain)
            throws ServletException , IOException{
        String header = request.getHeader("Authorization");
         try{
        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);

            System.out.println("validate token: "+jwtService.validatetoken(token));
            if(jwtService.validatetoken(token)){
                String userName= jwtService.extractUserName(token);
                if (
                        userName != null && SecurityContextHolder.getContext().getAuthentication() == null
                ) {
                    Users userDetails = customUserService.loadUserByUsername(userName);
                    System.out.println("username details  : " + userDetails.toString());
                     //build auth object
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    //pass auth object to context so that it is available it furthur processing of request
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            }
        } } catch (Exception ex) {

        request.setAttribute(
                "custom_jwt_exception",
                ex
        );
    }

        filterChain.doFilter(request,response);
    }

}
