package com.joaocarlos.email_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmailOciDTO(
        String recipientEmail,
        String senderEmail,
        String recipientName,
        String subject,
        String body
) {
}
