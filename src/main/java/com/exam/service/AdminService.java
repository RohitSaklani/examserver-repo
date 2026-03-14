package com.exam.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService  {


    @Autowired
    private PasswordEncoder passwordEncoder;





}
