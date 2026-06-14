package com.exam.controller;

import com.exam.DTO.LoginDTO;
import com.exam.service.AuthService;
import com.exam.service.JwtService;
import com.exam.util.ApiResponse;
import com.exam.util.AuthSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${jwt.expiration}")
    private long expiration;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/auth/login")
    public ResponseEntity<AuthSuccessResponse> userLogin(@RequestBody LoginDTO dto) {
        System.out.println("login data : " + dto.toString());

        Map<String,String> tokenData = authService.userLogin(
                dto.getUsername(),
                dto.getPassword()
        );

      System.out.println("toke data "+tokenData);


        return ResponseEntity.status(HttpStatus.OK).body(new AuthSuccessResponse(
                tokenData.get("token"),
                "Bearer",
                DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(Instant.ofEpochMilli(System.currentTimeMillis() + expiration)),
                "login Successfull",
                tokenData.get("ROLE")));
    }


    @PostMapping("/auth/admin/login")
    public ResponseEntity<AuthSuccessResponse> adminLogin(@RequestBody LoginDTO dto) {

        System.out.println("login data : " + dto.toString());

        Map<String,String> tokenData = authService.userLogin(
                dto.getUsername(),
                dto.getPassword()
        );

        System.out.println("token data "+tokenData);


        return ResponseEntity.status(HttpStatus.OK).body(new AuthSuccessResponse(
                tokenData.get("token"),
                "Bearer",
                DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                        .format(Instant.ofEpochMilli(System.currentTimeMillis() + expiration)),
                "login Successfull",
                tokenData.get("ROLE")));
    }


    @GetMapping("/auth/verify")
    public ResponseEntity<ApiResponse> verifyToken(Authentication auth) {


        Map data = new HashMap();
        data.put("isAuthenticated", true);
        data.put("username", auth.getName());

        ApiResponse res = new ApiResponse();
        res.setData(data);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


}
