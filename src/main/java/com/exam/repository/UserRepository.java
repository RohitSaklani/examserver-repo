package com.exam.repository;


import com.exam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    public Optional<User> findByUserNameAndEmail(String userName, String email);


    public Optional<User> findByUserName(String userName);
}
