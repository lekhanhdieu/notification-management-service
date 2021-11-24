package com.example.notificationmanagementservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "attach_files")
public class AttachFileEntity implements Serializable {
    private static final long serialVersionUID = 344881855277285582L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // để k xử lý khi trả về json
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="notice_id")
    private NoticeEntity noticeEntity;
}
