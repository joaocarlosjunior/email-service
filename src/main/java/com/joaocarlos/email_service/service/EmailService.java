package com.joaocarlos.email_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.SameEmailException;
import com.joaocarlos.email_service.exceptions.SerializeJsonException;
import com.joaocarlos.email_service.strategy.email_service.factory.EmailServiceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
@RefreshScope
public class EmailService {
    @Value("${mail.integracao}")
    private String EMAIL_SERVICE;
    private final EmailServiceFactory emailServiceFactory;

    public EmailService(EmailServiceFactory emailServiceFactory) {
        this.emailServiceFactory = emailServiceFactory;
    }

    public void sendEmail(EmailDTO emailDTO) {
        if (emailDTO.recipient().equalsIgnoreCase(emailDTO.sender())) {
            throw new SameEmailException("Same sender and recipient email");
        }

        try {
            emailServiceFactory.getEmailStrategy(EMAIL_SERVICE).sendEmail(emailDTO);
        } catch (JsonProcessingException e) {
            throw new SerializeJsonException("Failed to serialize email into JSON object");
        }
    }
}
