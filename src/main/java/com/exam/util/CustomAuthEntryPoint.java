package com.exam.util;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint , AccessDeniedHandler {


    //for authntication exceptions
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {
        JwtException jwtEx =
                (JwtException)
                        request.getAttribute(
                                "custom_jwt_exception"
                        );
        System.out.println("type of jwtEx  :" + jwtEx);
        System.out.println("message inside commence : " + ex.getMessage() + "   type : " + ex.getClass().getSimpleName());

        Map<String, Object> body =
                new HashMap<>();



        body.put(
                "isAuthenticated",
                false
        );


        if (jwtEx != null) {
            if (jwtEx instanceof ExpiredJwtException) {

                body.put(
                        "error",
                        "Token expired"
                );

            } else if (jwtEx instanceof
                    MalformedJwtException) {

                body.put(
                        "error",
                        "Invalid Token "
                );

            } else if (jwtEx instanceof
                    SignatureException) {

                body.put(
                        "error",
                        "Invalid Token signature"
                );

            } else {

                body.put(
                        "error",
                        "Invalid Token "
                );
            }
        } else {

            body.put(
                    "error",
                    "Authetication Required"
            );
        }

        ObjectMapper mapper =
                new ObjectMapper();

        response.setStatus(
                HttpServletResponse
                        .SC_UNAUTHORIZED
        );

        response.setContentType(
                "application/json"
        );

        response.getWriter()
                .write(
                        mapper.writeValueAsString(
                                body
                        )
                );

    }


//    response.getWriter()
//            .write("""
//            {
//              "error":
//              "Authentication required"
//            }
//            """);
//
//        if(ex instanceof InsufficientAuthenticationException){
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//
//            response.getWriter().write("{\"error\":\"No Authentication Present \"}");
//        }else {
//
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//
//            response.getWriter().write("{\"error\":\"Invalid username or password\"}");
//        }






    //for authorization exceptions
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"Access denied\"}");
    }
}
