package com.example.springbootmultithreading.repository;

import com.example.springbootmultithreading.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
