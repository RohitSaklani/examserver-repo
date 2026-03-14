package com.exam.controller;


import com.exam.DTO.LoginDTO;
import com.exam.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;





    @GetMapping("/")
    public String hello(){
        return "hello admin";
    }



}
