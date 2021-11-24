package com.example.notificationmanagementservice.service;

import com.example.notificationmanagementservice.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity getUser(String name);

    List<UserEntity> getAll();

    UserEntity createUser(UserEntity userEntity);

    void updateUser(UserEntity userEntity);

    void deleteUser(Long id);

}
