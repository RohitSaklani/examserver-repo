package com.exam.service;

import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.exam.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<User> user = userRepository.findByUserName(username);

        if(user.isPresent()) {
            User u = user.get();
            System.out.println("user "+ user.get().toString());
            return new CustomUserDetails(
                    u.getUserName(),
                    u.getPassword(),
                    u.getRole().toString()
            );
        }
        throw new UsernameNotFoundException("User not found");
    }


}
