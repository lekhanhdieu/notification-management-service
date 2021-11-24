package com.example.notificationmanagementservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface  AttachFileService {
    public void save(MultipartFile file);
    public void init();
    public void deleteAll();
}
