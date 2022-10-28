package com.pornpimon.stockbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pornpimon.stockbackend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);
    
}
