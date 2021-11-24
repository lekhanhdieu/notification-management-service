package com.example.notificationmanagementservice.service.impl;

import com.example.notificationmanagementservice.entity.UserEntity;
import com.example.notificationmanagementservice.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenGetUser_success() {
        UserEntity user = new UserEntity();
        user.setName("abc");
        Mockito.when(userRepository.findByName("abc")).thenReturn(user);
        UserEntity user1 = new UserEntity();
        user1 = userService.getUser("abc");
        Assert.assertEquals(user1.getName(), user.getName());
    }

    @Test
    public void whenGetCreateUser_success() {
        UserEntity user = new UserEntity();
        user.setPassword("123");
        UserEntity userMock = new UserEntity();
        Mockito.when(userRepository.save(any())).thenReturn(user);
        Mockito.when(passwordEncoder.encode(any())).thenReturn("user");
        userMock = userService.createUser(user);
        Assert.assertEquals(user, userMock);
    }

    @Test
    public void whenGetAllUser_success() {
        List<UserEntity> user = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(user);
        List<UserEntity> actualUser = new ArrayList<>();
        actualUser = userService.getAll();
        Assert.assertEquals(actualUser, user);
    }

    @Test
    public void whenGetUpdateUser_success() {
        UserEntity user = new UserEntity();
        user.setAge("23");
        user.setPhone("01230123");
        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(user));
        userService.updateUser(user);
        Assert.assertEquals(user.getAge(), "23");
    }

    @Test
    public void whenGetDeleteUser_success() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(user));
        userService.deleteUser(1L);
    }
}
