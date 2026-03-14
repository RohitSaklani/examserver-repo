package com.exam.service;

import com.exam.Exception.UserAlreadyExistException;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {

        Optional<User> localUser = userRepository.findByUserNameAndEmail(user.getUserName(),user.getEmail());
        User createdUser=null;
        System.out.println("user in service "+localUser);
        if(localUser.isPresent()){
            throw new UserAlreadyExistException("User already present");
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
           createdUser =  userRepository.save(user);

        }

        return createdUser;
    }
}
