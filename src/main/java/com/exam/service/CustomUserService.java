package com.exam.service;

import com.exam.model.Users;
import com.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Users loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<Users> user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            Users u = user.get();
            System.out.println("user "+ user.get().toString());
            return new Users(
                    u.getUsername(),
                    u.getPassword(),
                    u.getRole()
            );
        }
        throw new UsernameNotFoundException("User not found");
    }
}
