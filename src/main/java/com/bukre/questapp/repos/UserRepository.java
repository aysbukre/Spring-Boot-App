package com.bukre.questapp.repos;

import com.bukre.questapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByUserName(String userName);
}
