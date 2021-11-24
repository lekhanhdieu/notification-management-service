package com.example.notificationmanagementservice.service.impl;

import com.example.notificationmanagementservice.entity.NoticeEntity;
import com.example.notificationmanagementservice.entity.UserEntity;
import com.example.notificationmanagementservice.dto.NoticeDto;
import com.example.notificationmanagementservice.repository.NoticeRepository;
import com.example.notificationmanagementservice.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoticeServiceImplTest {
    private final Path root = Paths.get("uploads");

    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NoticeServiceImpl noticeServiceImpl;

    @Test
    public void createNotice_successful() throws ParseException {
        UserDetails applicationUser = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);
        MockMultipartFile file
                = new MockMultipartFile(
                "test",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "test".getBytes()
        );
        UserEntity user = new UserEntity();
        user.setUsername("test");
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        MultipartFile[] multipartFile = {file};
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setContent("abc");
        noticeDto.setTitle("abc123");
        noticeDto.setAttachFiles(multipartFile);
        noticeDto.setEndDate("2021-11-11 00:00:00");
        noticeDto.setStartDate("2021-10-10 00:00:00");
        noticeDto.setAttachFiles(multipartFile);
        noticeServiceImpl.createNotice(noticeDto);
    }

    @Test(expected = NullPointerException.class)
    public void whenCallCreateNotice_ThrowError() throws ParseException {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        MultipartFile[] multipartFile = new MultipartFile[0];
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setContent("abc");
        noticeDto.setTitle("abc123");
        noticeDto.setAttachFiles(multipartFile);
        noticeDto.setEndDate("2021-11-11 00:00:00");
        noticeDto.setStartDate("2021-10-10 00:00:00");
        noticeServiceImpl.createNotice(noticeDto);
    }

    @Test
    public void whenCallUpdateNotice_successfull() throws Exception {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setContent("123");
        noticeEntity.setTitle("abc");
        Mockito.when(noticeRepository.findById(any())).thenReturn(Optional.of(noticeEntity));
        noticeServiceImpl.updateNotice(noticeEntity);
    }

    @Test(expected = NullPointerException.class)
    public void whenCallUpdateNotice_throwError() throws Exception {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setContent("123");
        noticeEntity.setTitle("abc");
        Mockito.when(noticeRepository.findById(any())).thenReturn(null);
        noticeServiceImpl.updateNotice(noticeEntity);
    }

    @Test
    public void whenCallDeleteNotice_successfull() throws Exception {
        NoticeEntity noticeEntity = new NoticeEntity();
        Mockito.when(noticeRepository.findById(any())).thenReturn(Optional.of(noticeEntity));
        noticeServiceImpl.deleteNotice(any());
    }

    @Test(expected = NullPointerException.class)
    public void deleteNotice_throwError() throws Exception {
        Mockito.when(noticeRepository.findById(any())).thenReturn(null);
        noticeServiceImpl.deleteNotice(any());
    }

    @Test
    public void getById_success() throws Exception {
        UserEntity user = new UserEntity();
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setTitle("abc");
        noticeEntity.setContent("123");
        noticeEntity.setUserEntity(user);
        noticeEntity.setNumberOfView(1);
        noticeEntity.setEndDate(new Date(2021,12,22));
        Mockito.when(noticeRepository.findById(any())).thenReturn(Optional.of(noticeEntity));
        noticeServiceImpl.getNotice(any());
    }

    @Test(expected = Exception.class)
    public void whenCallGetById_throwError() throws Exception {
        NoticeEntity noticeEntity = setDataNoticeEntity();
        Mockito.when(noticeRepository.findById(any())).thenReturn(Optional.of(noticeEntity));
        noticeServiceImpl.getNotice(any());
    }

    private NoticeEntity setDataNoticeEntity() {
        NoticeEntity noticeEntity = new NoticeEntity();
        UserEntity user = new UserEntity();
        user.setAge("32");
        user.setPhone("012012");
        user.setUsername("asas");
        noticeEntity.setTitle("abc");
        noticeEntity.setContent("mnbm");
        noticeEntity.setUserEntity(user);
        noticeEntity.setEndDate(new Date());
        return noticeEntity;
    }

}
