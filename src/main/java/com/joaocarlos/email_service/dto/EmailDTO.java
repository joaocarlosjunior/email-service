package com.joaocarlos.email_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EmailDTO(
        @NotBlank(message = "Email destinatário obrigatório")
        @NotNull(message = "Email destinatário não pode ser null")
        @Email
        String recipient,
        @NotBlank(message = "Email remetente obrigatório")
        @NotNull(message = "Email remetente não pode ser null")
        @Email
        String sender,
        @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "Nome destinatário deve conter apenas letras")
        String recipientName,
        String subject,
        String content
) {
}
