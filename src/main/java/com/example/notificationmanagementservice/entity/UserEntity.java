package com.example.notificationmanagementservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Data
@Table(name = "user_notice")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<NoticeEntity> noticeEntities;
}
