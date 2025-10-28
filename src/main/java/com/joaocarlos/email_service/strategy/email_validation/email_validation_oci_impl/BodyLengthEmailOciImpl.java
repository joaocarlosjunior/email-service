package com.joaocarlos.email_service.strategy.email_validation.email_validation_oci_impl;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import com.joaocarlos.email_service.strategy.email_validation.EmailValidationStrategy;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class BodyLengthEmailOciImpl implements EmailValidationStrategy {
    @Override
    public void execute(@Valid EmailDTO emailDTO) {
        if(emailDTO.content().length() > 250){
            throw new EmailServiceException("Conteúdo email deve ter no máximo 250 caracteres");
        }
    }

    @Override
    public String getEmailServiceName() {
        return "OCI";
    }
}
