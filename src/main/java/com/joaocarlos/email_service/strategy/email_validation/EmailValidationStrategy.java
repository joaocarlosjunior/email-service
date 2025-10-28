package com.joaocarlos.email_service.strategy.email_validation;

import com.joaocarlos.email_service.dto.EmailDTO;

public interface EmailValidationStrategy {
    void execute(EmailDTO emailDTO);
    String getEmailServiceName();
}
