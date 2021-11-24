package com.example.notificationmanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponseDto {
    private String title;
    private String content;
    private Date registrationDate;
    private Integer numberOfView;
    private String author;

}
