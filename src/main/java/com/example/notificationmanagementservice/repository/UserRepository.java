package com.example.notificationmanagementservice.repository;

import com.example.notificationmanagementservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByName(String name);
    UserEntity findByUsername(String username);
}
