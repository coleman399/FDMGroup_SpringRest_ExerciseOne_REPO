package com.fdmgroup.spring_rest_exercise_one.repository;

import com.fdmgroup.spring_rest_exercise_one.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
