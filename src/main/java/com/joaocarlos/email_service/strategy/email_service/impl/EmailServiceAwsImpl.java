package com.joaocarlos.email_service.strategy.email_service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaocarlos.email_service.dto.EmailAwsDTO;
import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.mappers.EmailMapper;
import com.joaocarlos.email_service.strategy.email_service.EmailServiceStrategy;
import com.joaocarlos.email_service.strategy.email_validation.factory.EmailValidationFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceAwsImpl implements EmailServiceStrategy {
    private final EmailValidationFactory emailValidationFactory;

    public EmailServiceAwsImpl(EmailValidationFactory emailValidationFactory) {
        this.emailValidationFactory = emailValidationFactory;
    }

    @Override
    public void sendEmail(EmailDTO emailDTO) throws JsonProcessingException {
        if (emailDTO == null) {
            throw new IllegalArgumentException("EmailDTO cannot be null");
        }

        emailValidationFactory.getEmailValidationStrategy("AWS")
                .forEach(emailValidation -> {
                    emailValidation.execute(emailDTO);
                });

        ObjectMapper objectMapper = new ObjectMapper();

        EmailAwsDTO emailAwsDTO = EmailMapper.INSTANCE.toAwsDTO(emailDTO);

        String json = objectMapper.writeValueAsString(emailAwsDTO);
        System.out.println(json);
    }

    @Override
    public String getNameServiceEmail() {
        return "AWS";
    }
}
