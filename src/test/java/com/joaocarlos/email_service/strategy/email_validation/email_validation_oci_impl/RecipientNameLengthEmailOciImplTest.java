package com.joaocarlos.email_service.strategy.email_validation.email_validation_oci_impl;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class RecipientNameLengthEmailOciImplTest {
    private RecipientNameLengthEmailOciImpl recipientNameLengthEmailOciImpl;

    @BeforeEach
    void setUp() {
        recipientNameLengthEmailOciImpl = new RecipientNameLengthEmailOciImpl();
    }

    @Test
    public void execute_WithValidEmailDTO_DoesNotAnyThrowsException() {
        assertDoesNotThrow(() -> recipientNameLengthEmailOciImpl.execute(EMAIL_DTO));
    }

    @Test
    public void execute_WithInvalidEmailDTO_ThrowsException() {
        EmailDTO emailDTO = new EmailDTO(
                "email_destinatario@email.com",
                "email_remetente@email.com",
                "Nome do destinatario com mais de 60 caracteres para testar validações de comprimento",
                "Assunto email",
                "Conteudo email");

        assertThrowsExactly(EmailServiceException.class, () -> recipientNameLengthEmailOciImpl.execute(emailDTO));
    }

    @Test
    public void execute_WithRecipientNameLengthExactly45_DoesNotThrowException() {
        String recipientName = "a".repeat(50);
        EmailDTO emailDTO = new EmailDTO(
                "email_destinatario@email.com",
                "email_remetente@email.com",
                recipientName,
                "Assunto email",
                "Conteudo email"
        );

        assertDoesNotThrow(() -> recipientNameLengthEmailOciImpl.execute(emailDTO));
    }

    @Test
    public void getEmailServiceName_ReturnsEmailServiceName() {
        String sut = recipientNameLengthEmailOciImpl.getEmailServiceName();

        assertThat(sut).isNotBlank();
        assertThat(sut).isEqualTo("OCI");
    }
}
