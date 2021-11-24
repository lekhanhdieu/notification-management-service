package com.example.notificationmanagementservice.api;

import com.example.notificationmanagementservice.entity.dto.NoticeDto;
import com.example.notificationmanagementservice.entity.NoticeEntity;
import com.example.notificationmanagementservice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createNotice(@ModelAttribute @RequestBody NoticeDto noticeEntity) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeService.createNotice(noticeEntity));
    }

    @PutMapping
    public ResponseEntity<?> updateNotice(@RequestBody NoticeEntity noticeEntity){
        noticeService.updateNotice(noticeEntity);
        return ResponseEntity.status(HttpStatus.OK).body("update successfull");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteNotice(@RequestParam Long id){
        noticeService.deleteNotice(id);
        return ResponseEntity.status(HttpStatus.OK).body("delete successfull");
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllNotices() {
        List<NoticeEntity> list = noticeService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getNotice(@PathVariable("id") Long id) throws Exception {
        NoticeEntity notice = new NoticeEntity();
        try {
            notice = noticeService.getNotice(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return new ResponseEntity<>(notice, HttpStatus.OK);
    }
}