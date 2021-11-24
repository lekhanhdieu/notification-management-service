package com.example.notificationmanagementservice.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "notice")
public class NoticeEntity implements Serializable {
    private static final long serialVersionUID = 344881855277285582L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "number_view")
    private Integer numberOfView;
    @Column(name = "author")
    private String author;
    @Column(name = "registration_date")
    private Date registrationDate;
    @OneToMany(mappedBy="noticeEntity",cascade=CascadeType.ALL)
    private List<AttachFileEntity> attachFiles;

}
