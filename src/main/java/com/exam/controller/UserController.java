package com.exam.controller;


import com.exam.model.Role;
import com.exam.model.User;
import com.exam.service.UserService;
import com.exam.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;



    @GetMapping("/")
    public String hello(){
        return "hello user";
    }

    @GetMapping("/check")
    public String Check(){
        return "check done";
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody @Valid User user ) throws Exception {
        ApiResponse res = new ApiResponse();
        System.out.println("user "+user.toString());

        user.setRole(Role.USER);

        User createdUser = null;

            createdUser  = userService.createUser(user);
            res.setMessage("User created successfully");
            res.setData(createdUser);


    return ResponseEntity.status(HttpStatus.OK).body(res);

    }
}
