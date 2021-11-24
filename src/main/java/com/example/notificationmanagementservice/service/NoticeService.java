package com.example.notificationmanagementservice.service;


import com.example.notificationmanagementservice.dto.NoticeResponseDto;
import com.example.notificationmanagementservice.entity.NoticeEntity;
import com.example.notificationmanagementservice.dto.NoticeDto;

import java.text.ParseException;
import java.util.List;

public interface NoticeService {

    NoticeEntity createNotice(NoticeDto userEntity) throws ParseException;

    void updateNotice(NoticeEntity userEntity) throws Exception;

    void deleteNotice(Long id);

    List<NoticeEntity> getAll();

    NoticeResponseDto getNotice(Long id) throws Exception;
}
