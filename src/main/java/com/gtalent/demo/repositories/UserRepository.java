package com.gtalent.demo.repositories;

import com.gtalent.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Integer> {
}
