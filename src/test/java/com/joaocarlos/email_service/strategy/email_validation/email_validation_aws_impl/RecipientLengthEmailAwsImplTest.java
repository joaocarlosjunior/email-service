package com.joaocarlos.email_service.strategy.email_validation.email_validation_aws_impl;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class RecipientLengthEmailAwsImplTest {
    private RecipientLengthEmailAwsImpl recipientLengthEmailAwsImpl;

    @BeforeEach
    public void setup() {
        recipientLengthEmailAwsImpl = new RecipientLengthEmailAwsImpl();
    }

    @Test
    public void execute_WithValidEmailDTO_DoesNotAnyThrowsException() {
        assertDoesNotThrow(() -> recipientLengthEmailAwsImpl.execute(EMAIL_DTO));
    }

    @Test
    public void execute_WithInvalidEmailDTO_ThrowsException() {
        EmailDTO emailDTO = new EmailDTO(
                "email_destinariario_com_mais_45_caracteres@email.com",
                "email_remetente@email.com",
                "Nome Destinatário",
                "Assunto email",
                "Conteudo email"
        );

        assertThrowsExactly(EmailServiceException.class, () -> recipientLengthEmailAwsImpl.execute(emailDTO));
    }

    @Test
    public void execute_WithRecipientLengthExactly45_DoesNotThrowException() {
        String recipient = "a".repeat(45);
        EmailDTO emailDTO = new EmailDTO(
                recipient,
                "email_remetente@email.com",
                "Nome Destinatário",
                "Assunto email",
                "Conteudo email"
        );

        assertDoesNotThrow(() -> recipientLengthEmailAwsImpl.execute(emailDTO));
    }

    @Test
    public void getEmailServiceName_ReturnsEmailServiceName() {
        String sut = recipientLengthEmailAwsImpl.getEmailServiceName();

        assertThat(sut).isNotBlank();
        assertThat(sut).isEqualTo("AWS");
    }
}
