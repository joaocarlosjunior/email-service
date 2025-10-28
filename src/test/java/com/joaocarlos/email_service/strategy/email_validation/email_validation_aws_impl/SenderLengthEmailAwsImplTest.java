package com.joaocarlos.email_service.strategy.email_validation.email_validation_aws_impl;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class SenderLengthEmailAwsImplTest {
    private SenderLengthEmailAwsImpl senderLengthEmailAwsImpl;

    @BeforeEach
    public void setup() {
        senderLengthEmailAwsImpl = new SenderLengthEmailAwsImpl();
    }

    @Test
    public void execute_WithValidEmailDTO_DoesNotAnyThrowsException() {
        assertDoesNotThrow(() -> senderLengthEmailAwsImpl.execute(EMAIL_DTO));
    }

    @Test
    public void execute_WithInvalidEmailDTO_ThrowsException() {
        EmailDTO emailDTO = new EmailDTO(
                "email_destinariario@email.com",
                "email_remetente_com_mais_45_caracteres@email.com",
                "Nome Destinatário",
                "Assunto email",
                "Conteudo email"
        );

        assertThrowsExactly(EmailServiceException.class, () -> senderLengthEmailAwsImpl.execute(emailDTO));
    }

    @Test
    public void execute_WithSenderLengthExactly45_DoesNotThrowException() {
        String sender = "a".repeat(45);
        EmailDTO emailDTO = new EmailDTO(
                "email_destinariario@email.com",
                sender,
                "Nome destinatario",
                "Assunto email",
                "Conteudo email"
        );

        assertDoesNotThrow(() -> senderLengthEmailAwsImpl.execute(emailDTO));
    }

    @Test
    public void getEmailServiceName_ReturnsEmailServiceName() {
        String sut = senderLengthEmailAwsImpl.getEmailServiceName();

        assertThat(sut).isNotBlank();
        assertThat(sut).isEqualTo("AWS");
    }
}
