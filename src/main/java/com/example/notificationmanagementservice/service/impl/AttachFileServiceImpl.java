package com.example.notificationmanagementservice.service.impl;

import com.example.notificationmanagementservice.service.AttachFileService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 *AttachFileServiceImpl
 *
 * @author FPT Software
 */

@Service
public class AttachFileServiceImpl implements AttachFileService {

    private final Path root = Paths.get("uploads");


    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException ignored) {

        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}
