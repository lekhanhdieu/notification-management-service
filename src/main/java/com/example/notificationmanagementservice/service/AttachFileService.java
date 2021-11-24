package com.example.notificationmanagementservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface  AttachFileService {
    void save(MultipartFile file);
    void init();
    void deleteAll();
}
