package com.example.notificationmanagementservice.repository;

import com.example.notificationmanagementservice.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
}
