package com.joaocarlos.email_service.strategy.email_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.joaocarlos.email_service.dto.EmailDTO;

public interface EmailServiceStrategy {
    void sendEmail(EmailDTO emailDTO) throws JsonProcessingException;
    String getNameServiceEmail();
}
