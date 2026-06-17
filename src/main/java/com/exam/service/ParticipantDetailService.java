package com.exam.service;

import com.exam.Exception.UserAlreadyExistException;
import com.exam.model.ParticipantDetail;
import com.exam.repository.ParticipantDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipantDetailService {

    @Autowired
    private ParticipantDetailRepository userDetailRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public ParticipantDetail createUserDetail(ParticipantDetail userDetail) {
        ParticipantDetail createdUserDetail=null;
        Optional<ParticipantDetail> localUser = userDetailRepository.findByEmail(userDetail.getEmail());

        System.out.println("user in service "+localUser);
        if(localUser.isPresent()){
            throw new UserAlreadyExistException("User already present");
        }else{

           createdUserDetail =  userDetailRepository.save(userDetail);

        }

        return createdUserDetail;
    }



}
