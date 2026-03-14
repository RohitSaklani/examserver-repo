package com.exam.config;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    protected  void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain)
            throws ServletException , IOException{
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer")){
            String token = header.substring(7);
            System.out.println("token: "+token);
            System.out.println("validate token: "+jwtUtil.validatetoken(token));
            if(jwtUtil.validatetoken(token)){
                String userName= jwtUtil.extractUserName(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                System.out.println("username : "+userName);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName,null,userDetails.getAuthorities() );
                System.out.println("is autheticated : "+auth.isAuthenticated());
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }

        filterChain.doFilter(request,response);
    }

}
