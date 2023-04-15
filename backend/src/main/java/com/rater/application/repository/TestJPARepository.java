package com.rater.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rater.application.model.TestJPAEntity;

public interface TestJPARepository extends JpaRepository<TestJPAEntity, Integer> {
    
}
