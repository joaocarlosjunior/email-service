package com.joaocarlos.email_service.strategy.email_validation.email_validation_aws_impl;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class ContentLengthEmailAwsImplTest {
    private ContentLengthEmailAwsImpl contentLengthEmailAwsImpl;

    @BeforeEach
    void setUp() {
        contentLengthEmailAwsImpl = new ContentLengthEmailAwsImpl();
    }

    @Test
    public void execute_WithValidEmailDTO_DoesNotAnyThrowsException() {
        assertDoesNotThrow(() -> contentLengthEmailAwsImpl.execute(EMAIL_DTO));
    }

    @Test
    public void execute_WithInvalidEmailDTO_ThrowsException() {
        EmailDTO emailDTO = new EmailDTO(
                "email_destinatario@email.com",
                "email_remetente@email.com",
                "Nome Destinatário",
                "Assunto email",
                "Esse texto contém mais de 256 caracteres, e estou escrevendo para testar validação de um campo que exige um tamanho mínimo maior, garantindo que o sistema consiga reconhecer corretamente os limites definidos e tratar o excesso de forma adequada, evitando falhas e comportamentos inesperados durante o processamento dos dados inseridos pelo usuário. Além disso, esse teste ajuda a assegurar a integridade das informações e a robustez da aplicação em diferentes cenários de uso.");

        assertThrowsExactly(EmailServiceException.class, () -> contentLengthEmailAwsImpl.execute(emailDTO));
    }

    @Test
    public void execute_WithContentLengthExactly256_DoesNotThrowException() {
        String content  = "a".repeat(256);
        EmailDTO emailDTO = new EmailDTO(
                "email_destinatario@email.com",
                "email_remetente@email.com",
                "Nome Destinatário",
                "Assunto email",
                content
        );

        assertDoesNotThrow(() -> contentLengthEmailAwsImpl.execute(emailDTO));
    }

    @Test
    public void getEmailServiceName_ReturnsEmailServiceName() {
        String sut = contentLengthEmailAwsImpl.getEmailServiceName();

        assertThat(sut).isNotBlank();
        assertThat(sut).isEqualTo("AWS");
    }
}
