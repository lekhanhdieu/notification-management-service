package com.example.notificationmanagementservice.service;


import com.example.notificationmanagementservice.entity.NoticeEntity;
import com.example.notificationmanagementservice.entity.dto.NoticeDto;

import java.text.ParseException;
import java.util.List;

public interface NoticeService {

    NoticeEntity createNotice(NoticeDto userEntity) throws ParseException;

    void updateNotice(NoticeEntity userEntity) throws Exception;

    void deleteNotice(Long id);

    List<NoticeEntity> getAll();

    NoticeEntity getNotice(Long id) throws Exception;
}
