package com.example.notificationmanagementservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@NoArgsConstructor
@Data
public class NoticeDto {
    private String title;
    private String content;
    private String startDate;
    private String endDate;
    private MultipartFile[] attachFiles;
}
