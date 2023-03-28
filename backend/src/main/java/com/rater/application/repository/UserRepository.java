package com.rater.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rater.application.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
}
