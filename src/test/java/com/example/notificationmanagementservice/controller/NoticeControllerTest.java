package com.example.notificationmanagementservice.controller;
import com.example.notificationmanagementservice.entity.NoticeEntity;
import com.example.notificationmanagementservice.entity.dto.NoticeDto;
import com.example.notificationmanagementservice.service.AttachFileService;
import com.example.notificationmanagementservice.service.CustomAccountService;
import com.example.notificationmanagementservice.service.NoticeService;
import com.example.notificationmanagementservice.service.UserService;
import com.example.notificationmanagementservice.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = NoticeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NoticeControllerTest {
    private static final String BASE_URL = "/notices";

    @MockBean
    private NoticeService noticeService;

    @MockBean
    private AttachFileService attachFileService;

    @MockBean
    private CustomAccountService customAccountService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testCreateNotice() throws Exception{
        NoticeEntity noticeEntity = new NoticeEntity();
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setContent("mnbmnb");
        noticeDto.setTitle("mnbbmnb");
        noticeDto.setEndDate("2021-03-12 10:20:30");
        noticeDto.setStartDate("2021-09-12 10:20:30");
        Mockito.when(noticeService.createNotice(noticeDto)).thenReturn(noticeEntity);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(noticeDto));
        mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGetAllNotice() throws Exception{
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setAuthor("aa");
        noticeEntity.setTitle("fsdfsfs");
        List<NoticeEntity> noticeEntities = new ArrayList<>();
        noticeEntities.add(noticeEntity);
        Mockito.when(noticeService.getAll()).thenReturn(noticeEntities);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testUpdateNotice() throws Exception{
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setContent("content");
        noticeEntity.setTitle("abc");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(noticeEntity));
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNotice() throws Exception{
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(BASE_URL+"?id=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void testGetNotice() throws Exception{
        NoticeEntity noticeEntity =  new NoticeEntity();
        when(noticeService.getNotice(any())).thenReturn(noticeEntity);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
