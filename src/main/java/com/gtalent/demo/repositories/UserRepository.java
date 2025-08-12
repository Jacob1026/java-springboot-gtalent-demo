package com.gtalent.demo.repositories;

import com.gtalent.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Integer> {
    //select users where username = xxx
    Optional<User>findByUsername(String username);
}
