package com.exam.controller;

import com.exam.DTO.LoginDTO;
import com.exam.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/user/login")
    public String userLogin(@RequestBody LoginDTO dto){
        System.out.println("login data : "+dto.toString());

        return authService.userLogin(
                dto.getUsername(),
                dto.getPassword()
        );
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestBody LoginDTO dto){

        System.out.println("login data : "+dto.toString());
        return authService.userLogin(
                dto.getUsername(),
                dto.getPassword()
        );
    }


}
