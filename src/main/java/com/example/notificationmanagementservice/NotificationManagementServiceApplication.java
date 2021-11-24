package com.example.notificationmanagementservice;

import com.example.notificationmanagementservice.service.AttachFileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class NotificationManagementServiceApplication implements CommandLineRunner {

    @Resource
    AttachFileService storageService;

    public static void main(String[] args) {
        SpringApplication.run(com.example.notificationmanagementservice.NotificationManagementServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        storageService.init();
    }
}
