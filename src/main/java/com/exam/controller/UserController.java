package com.exam.controller;


import com.exam.DTO.ResultDTO;
import com.exam.DTO.UserDetailDTO;
import com.exam.service.ResultService;
import com.exam.service.UserService;
import com.exam.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ResultService resultService;


    @GetMapping("/")
    public String hello(){
        return "hello user";
    }

    @GetMapping("/check")
    public String Check(){
        return "check done";
    }


    @PostMapping("/user/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody @Valid UserDetailDTO userDetailDTO ) throws Exception {
        ApiResponse res = new ApiResponse();
        System.out.println("user "+userDetailDTO.toString());

            boolean created  = userService.createUserDetail(userDetailDTO);
            if(created) {
                res.setMessage("User created successfully");
            }

    return ResponseEntity.status(HttpStatus.OK).body(res);

    }

    @GetMapping("/user/info")
    public ResponseEntity<ApiResponse> getUserDetails(){

            UserDetailDTO userDetailDTO = userService.getUserDetailsByContext();
            ApiResponse res = new ApiResponse();
            res.setData(userDetailDTO);
            res.setMessage("success");
            return  ResponseEntity.status(HttpStatus.OK).body(res);


    }


    @PatchMapping("/user/info")
    public ResponseEntity<ApiResponse> updateUserDetails(@RequestBody UserDetailDTO userDetailDTO){

        UserDetailDTO updatdUserDetailDTO = userService.updateUserDetailsByContext(userDetailDTO);
        System.out.println("updatdUserDetailDTO : "+updatdUserDetailDTO.toString());
        ApiResponse res = new ApiResponse();
        res.setData(updatdUserDetailDTO);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);


    }

    @GetMapping("/user/progress")
    public ResponseEntity<ApiResponse> getProgressData(){
        List<ResultDTO> results = resultService.findResultByUser();
        ApiResponse res = new ApiResponse();
        res.setData(results);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }



}
