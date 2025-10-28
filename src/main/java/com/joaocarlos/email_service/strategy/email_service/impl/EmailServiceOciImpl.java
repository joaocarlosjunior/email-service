package com.joaocarlos.email_service.strategy.email_service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.dto.EmailOciDTO;
import com.joaocarlos.email_service.mappers.EmailMapper;
import com.joaocarlos.email_service.strategy.email_service.EmailServiceStrategy;
import com.joaocarlos.email_service.strategy.email_validation.factory.EmailValidationFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceOciImpl implements EmailServiceStrategy {
    private final EmailValidationFactory emailValidationFactory;

    public EmailServiceOciImpl(EmailValidationFactory emailValidationFactory) {
        this.emailValidationFactory = emailValidationFactory;
    }

    @Override
    public void sendEmail(EmailDTO emailDTO) throws JsonProcessingException {
        if (emailDTO == null) {
            throw new IllegalArgumentException("EmailDTO cannot be null");

        }

        emailValidationFactory.getEmailValidationStrategy("OCI")
                .forEach(emailValidation -> {
                    emailValidation.execute(emailDTO);
                });

        ObjectMapper objectMapper = new ObjectMapper();

        EmailOciDTO emailOciDTO = EmailMapper.INSTANCE.toOciDTO(emailDTO);

        String json = objectMapper.writeValueAsString(emailOciDTO);
        System.out.println(json);
    }

    @Override
    public String getNameServiceEmail() {
        return "OCI";
    }
}
