package com.joaocarlos.email_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmailAwsDTO(
        String recipient,
        String sender,
        String recipientName,
        String subject,
        String content
) {
}
