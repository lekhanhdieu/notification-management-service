package com.example.notificationmanagementservice.service.impl;

import com.example.notificationmanagementservice.repository.UserRepository;
import com.example.notificationmanagementservice.entity.UserEntity;
import com.example.notificationmanagementservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * UserServiceImpl
 *
 * @author FPT Software
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        UserEntity user = userRepository.findByUsername(userEntity.getUsername()).orElse(null);
        if (user != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "already exist");
        }
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
