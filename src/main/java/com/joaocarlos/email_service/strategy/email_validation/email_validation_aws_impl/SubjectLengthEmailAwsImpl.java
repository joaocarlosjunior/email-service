package com.joaocarlos.email_service.strategy.email_validation.email_validation_aws_impl;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import com.joaocarlos.email_service.strategy.email_validation.EmailValidationStrategy;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class SubjectLengthEmailAwsImpl implements EmailValidationStrategy {
    @Override
    public void execute(@Valid EmailDTO emailDTO) {
        if (emailDTO.subject().length() > 120) {
            throw new EmailServiceException("Assunto deve ter no máximo 120 caracteres");
        }
    }

    @Override
    public String getEmailServiceName() {
        return "AWS";
    }
}
