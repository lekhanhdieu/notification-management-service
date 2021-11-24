package com.example.notificationmanagementservice.controller;

import com.example.notificationmanagementservice.dto.AuthRequest;
import com.example.notificationmanagementservice.service.AttachFileService;
import com.example.notificationmanagementservice.service.CustomAccountService;
import com.example.notificationmanagementservice.service.NoticeService;
import com.example.notificationmanagementservice.service.UserService;
import com.example.notificationmanagementservice.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = JwtController.class)
@AutoConfigureMockMvc(addFilters = false)
@WebAppConfiguration
public class JwtControllerTest {
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

    @MockBean(name = "customAccountService")
    private UserDetailsService userDetailsService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void whenCallCreateUser_success() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("aa");
        authRequest.setUsername("aaa");
        UserDetails applicationUser = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(applicationUser);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateToken(any())).thenReturn("authentication");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(authRequest));
        mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void whenCallCreateUser_throwError() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("123");
        authRequest.setUsername("abc");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(authRequest.getUsername()));
        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
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
