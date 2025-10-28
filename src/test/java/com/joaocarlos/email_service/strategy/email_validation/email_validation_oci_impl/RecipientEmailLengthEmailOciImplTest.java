package com.joaocarlos.email_service.strategy.email_validation.email_validation_oci_impl;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class RecipientEmailLengthEmailOciImplTest {
    private RecipientEmailLengthEmailOciImpl recipientEmailLengthEmailOciImpl;

    @BeforeEach
    void setUp() {
        recipientEmailLengthEmailOciImpl = new RecipientEmailLengthEmailOciImpl();
    }

    @Test
    public void execute_WithValidEmailDTO_DoesNotAnyThrowsException() {
        assertDoesNotThrow(() -> recipientEmailLengthEmailOciImpl.execute(EMAIL_DTO));
    }

    @Test
    public void execute_WithInvalidEmailDTO_ThrowsException() {
        EmailDTO emailDTO = new EmailDTO(
                "email_destinariario_com_mais_40_caracteres@email.com",
                "email_remetente@email.com",
                "Nome Destinatário",
                "Assunto email",
                "Conteudo email");

        assertThrowsExactly(EmailServiceException.class, () -> recipientEmailLengthEmailOciImpl.execute(emailDTO));
    }

    @Test
    public void execute_WithRecipientEmailLengthExactly45_DoesNotThrowException() {
        String recipientEmail = "a".repeat(40);
        EmailDTO emailDTO = new EmailDTO(
                recipientEmail,
                "email_remetente@email.com",
                "Nome Destinatário",
                "Assunto email",
                "Conteudo email"
        );

        assertDoesNotThrow(() -> recipientEmailLengthEmailOciImpl.execute(emailDTO));
    }

    @Test
    public void getEmailServiceName_ReturnsEmailServiceName() {
        String sut = recipientEmailLengthEmailOciImpl.getEmailServiceName();

        assertThat(sut).isNotBlank();
        assertThat(sut).isEqualTo("OCI");
    }
}
