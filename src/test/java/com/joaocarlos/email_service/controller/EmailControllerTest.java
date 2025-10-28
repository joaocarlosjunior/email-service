package com.joaocarlos.email_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaocarlos.email_service.exceptions.InvalidEmailStrategyException;
import com.joaocarlos.email_service.exceptions.SameEmailException;
import com.joaocarlos.email_service.exceptions.SerializeJsonException;
import com.joaocarlos.email_service.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailController.class)
public class EmailControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private EmailService emailService;

    @Test
    public void sendEmail_WithValidData_ReturnsNoContent() throws Exception {
        mockMvc.perform(post("/api/v1/email")
                        .content(objectMapper.writeValueAsString(EMAIL_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void sendEmail_WhenSameEmail_ReturnsConflict() throws Exception {
        doThrow(SameEmailException.class).when(emailService).sendEmail(EMAIL_DTO);

        mockMvc.perform(post("/api/v1/email")
                        .content(objectMapper.writeValueAsString(EMAIL_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void sendEmail_WhenErrorConfigProppertyEmail_ReturnsInternalServerError() throws Exception {
        doThrow(InvalidEmailStrategyException.class).when(emailService).sendEmail(EMAIL_DTO);

        mockMvc.perform(post("/api/v1/email")
                        .content(objectMapper.writeValueAsString(EMAIL_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void sendEmail_RequestEmailDTONull_ReturnsInternalServerError() throws Exception {
        doThrow(SerializeJsonException.class).when(emailService).sendEmail(EMAIL_DTO);

        mockMvc.perform(post("/api/v1/email")
                        .content(objectMapper.writeValueAsString(EMAIL_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
