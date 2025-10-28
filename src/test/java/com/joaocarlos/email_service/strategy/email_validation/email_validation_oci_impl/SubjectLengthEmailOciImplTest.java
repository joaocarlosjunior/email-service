package com.joaocarlos.email_service.strategy.email_validation.email_validation_oci_impl;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class SubjectLengthEmailOciImplTest {
    private SubjectLengthEmailOciImpl subjectLengthEmailOciImpl;

    @BeforeEach
    public void setup() {
        subjectLengthEmailOciImpl = new SubjectLengthEmailOciImpl();
    }

    @Test
    public void execute_WithValidEmailDTO_DoesNotAnyThrowsException() {
        assertDoesNotThrow(() -> subjectLengthEmailOciImpl.execute(EMAIL_DTO));
    }

    @Test
    public void execute_WithInvalidEmailDTO_ThrowsException() {
        EmailDTO emailDTO = new EmailDTO(
                "email_destinariario@email.com",
                "email_remetente_com_mais_45_caracteres@email.com",
                "Nome Destinatário",
                "Esse texto contem mais de 120 caracteres, e estou escrevendo para testar validacao de um campo que exige um tamanho minimo maior, apenas para garantir que o sistema consiga identificar corretamente quando o limite é atingido.",
                "Conteudo email"
        );

        assertThrowsExactly(EmailServiceException.class, () -> subjectLengthEmailOciImpl.execute(emailDTO));
    }

    @Test
    public void execute_WithSubjectLengthExactly45_DoesNotThrowException() {
        String subject = "a".repeat(100);
        EmailDTO emailDTO = new EmailDTO(
                "email_destinariario@email.com",
                "email_remetente@email.com",
                "Nome Destinatário",
                subject,
                "Conteudo email"
        );

        assertDoesNotThrow(() -> subjectLengthEmailOciImpl.execute(emailDTO));
    }

    @Test
    public void getEmailServiceName_ReturnsEmailServiceName() {
        String sut = subjectLengthEmailOciImpl.getEmailServiceName();

        assertThat(sut).isNotBlank();
        assertThat(sut).isEqualTo("OCI");
    }
}
