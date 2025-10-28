package com.joaocarlos.email_service.controller;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.service.EmailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@Validated
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Void> sendEmail(@NotNull @Valid @RequestBody EmailDTO emailDTO) {
        emailService.sendEmail(emailDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
