package com.example.notificationmanagementservice.service.Impl;

import com.example.notificationmanagementservice.entity.AttachFileEntity;
import com.example.notificationmanagementservice.entity.NoticeEntity;
import com.example.notificationmanagementservice.entity.dto.NoticeDto;
import com.example.notificationmanagementservice.repository.NoticeRepository;
import com.example.notificationmanagementservice.service.AttachFileService;
import com.example.notificationmanagementservice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *NoticeServiceImpl
 *
 * @author FPT Software
 */

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {

    private final Path root = Paths.get("uploads");

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private AttachFileService attachFileService;

    /**
     * Create new notice
     *
     * @param input input
     * @return A string representing the identifier to use
     * @throws ParseException If string's byte cannot be obtained
     */
    @Override
    public NoticeEntity createNotice(NoticeDto input) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //check jwt
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        String username = userDetails.getUsername();
        Date date = new Date();
        //set data for noticeEntity
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setContent(input.getContent());
        noticeEntity.setTitle(input.getTitle());
        noticeEntity.setStartDate(formatter.parse(input.getStartDate()));
        noticeEntity.setEndDate(formatter.parse(input.getEndDate()));
        noticeEntity.setRegistrationDate(date);
        noticeEntity.setAuthor(username);
        noticeEntity.setNumberOfView(0);
        //set attach files
        List<AttachFileEntity> attachFileEntities = saveFile(input.getAttachFiles());
        for (AttachFileEntity attachFileEntity : attachFileEntities) {
            attachFileEntity.setNoticeEntity(noticeEntity);
        }
        noticeEntity.setAttachFiles(attachFileEntities);
        noticeRepository.save(noticeEntity);
        return noticeEntity;
    }

    /**
     * Update notice
     *
     * @param noticeEntity input
     * @throws Exception If string's byte cannot be obtained
     */
    @Override
    public void updateNotice(NoticeEntity noticeEntity) throws Exception {
        NoticeEntity notice = noticeRepository.findById(noticeEntity.getId()).orElseThrow(() -> new Exception("this notice doesn't exist"));
        log.info("Start findById result {}", notice);
        if (notice != null) {
            notice.setEndDate(noticeEntity.getEndDate());
            notice.setStartDate(noticeEntity.getStartDate());
            notice.setTitle(noticeEntity.getTitle());
            notice.setContent(noticeEntity.getContent());
            noticeRepository.save(notice);
        }
    }
    /**
     * Delete notice new notice
     *
     * @param id Note id
     */
    @Override
    public void deleteNotice(Long id) {
        noticeRepository.findById(id).ifPresent(noticeEntity -> noticeRepository.delete(noticeEntity));
    }

    /**
     * Get notice new notice before endDate
     *
     */
    @Override
    public List<NoticeEntity> getAll() {
        List<NoticeEntity> noticeEntities = new ArrayList<>();
        List<NoticeEntity> entityList = noticeRepository.findAll();
        log.info("Start findAll result {}",  entityList);
        for (NoticeEntity noticeEntity : entityList) {
            Date date = new Date();
            //check endDate
            if (!noticeEntity.getEndDate().before(date)) {
                noticeEntities.add(noticeEntity);
            }
        }

        return noticeEntities;
    }
    /**
     * Get notice before endDate
     *
     * @param id input
     * @throws Exception If string's byte cannot be obtained
     */
    @Override
    public NoticeEntity getNotice(Long id) throws Exception {
        NoticeEntity notice = noticeRepository.findById(id).orElseThrow(() -> new Exception("this notice doesn't exist"));
        log.info("Start findById result {}", notice);
        Date date = new Date();
        if (Objects.requireNonNull(notice).getEndDate().before(date)) {
            throw new Exception("this notice has expired");
        }
        notice.setNumberOfView(notice.getNumberOfView() + 1);
        noticeRepository.saveAndFlush(notice);
        log.info("Start saveAndFlush success with result {}: ", notice );
        return notice;
    }

    private List<AttachFileEntity> saveFile(MultipartFile[] files) {
        List<AttachFileEntity> attachFileEntities = new ArrayList<>();
        Arrays.asList(files).forEach(file -> {
            attachFileService.save(file);
            //save file into db
            try {
                AttachFileEntity attachFileEntity = new AttachFileEntity();
                Path pathFile = root.resolve(Objects.requireNonNull(file.getOriginalFilename()));
                Resource resource = new UrlResource(pathFile.toUri());
                attachFileEntity.setName(resource.getURL().getFile());
                attachFileEntities.add(attachFileEntity);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            } catch (IOException e) {
                log.debug("Exception {}", e.getMessage());
            }

        });
        return attachFileEntities;

    }
}
