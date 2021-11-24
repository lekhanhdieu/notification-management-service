package com.example.notificationmanagementservice.controller;

import com.example.notificationmanagementservice.service.UserService;
import com.example.notificationmanagementservice.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User management
 *
 * @author FPT Software
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/{name}")
    public ResponseEntity<UserEntity> getUser(@PathVariable("name") String name){
        UserEntity user = userService.getUser(name);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public @ResponseBody List<UserEntity> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userEntity));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity){
        userService.updateUser(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body("update successful");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("delete successful");
    }

}
