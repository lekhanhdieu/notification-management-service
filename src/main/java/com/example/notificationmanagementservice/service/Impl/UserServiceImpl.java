package com.example.notificationmanagementservice.service.Impl;

import com.example.notificationmanagementservice.repository.UserRepository;
import com.example.notificationmanagementservice.entity.UserEntity;
import com.example.notificationmanagementservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *UserServiceImpl
 *
 * @author FPT Software
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserEntity getUser(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        UserEntity userUpdate = userRepository.findById(userEntity.getId()).orElse(null);
        log.info("Start findById result {}", userUpdate);
        if (userUpdate != null) {
            userUpdate.setAge(userEntity.getAge());
            userUpdate.setPhone(userEntity.getPhone());
            userRepository.save(userUpdate);
        }

    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(userEntity -> userRepository.delete(userEntity));
    }
}
