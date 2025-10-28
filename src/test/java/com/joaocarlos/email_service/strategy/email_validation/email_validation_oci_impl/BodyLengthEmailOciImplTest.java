package com.joaocarlos.email_service.strategy.email_validation.email_validation_oci_impl;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class BodyLengthEmailOciImplTest {
    private BodyLengthEmailOciImpl bodyLengthEmailOciImpl;

    @BeforeEach
    void setUp() {
        bodyLengthEmailOciImpl = new BodyLengthEmailOciImpl();
    }

    @Test
    public void execute_WithValidEmailDTO_DoesNotAnyThrowsException() {
        assertDoesNotThrow(() -> bodyLengthEmailOciImpl.execute(EMAIL_DTO));
    }

    @Test
    public void execute_WithInvalidEmailDTO_ThrowsException() {
        EmailDTO emailDTO = new EmailDTO(
                "email_destinatario@email.com",
                "email_remetente@email.com",
                "Nome Destinatário",
                "Assunto email",
                "Esse texto contém mais de 250 caracteres, e estou escrevendo para testar validação de um campo que exige um tamanho mínimo maior, garantindo que o sistema consiga reconhecer corretamente os limites definidos e tratar o excesso de forma adequada, evitando falhas e comportamentos inesperados durante o processamento dos dados inseridos pelo usuário. Além disso, esse teste ajuda a assegurar a integridade das informações e a robustez da aplicação em diferentes cenários de uso.");

        assertThrowsExactly(EmailServiceException.class, () -> bodyLengthEmailOciImpl.execute(emailDTO));
    }

    @Test
    public void execute_WithBodyLengthExactly45_DoesNotThrowException() {
        String body = "a".repeat(250);
        EmailDTO emailDTO = new EmailDTO(
                "email_destinatario@email.com",
                "email_remetente@email.com",
                "Nome Destinatário",
                "Assunto email",
                body
        );

        assertDoesNotThrow(() -> bodyLengthEmailOciImpl.execute(emailDTO));
    }

    @Test
    public void getEmailServiceName_ReturnsEmailServiceName() {
        String sut = bodyLengthEmailOciImpl.getEmailServiceName();

        assertThat(sut).isNotBlank();
        assertThat(sut).isEqualTo("OCI");
    }
}
