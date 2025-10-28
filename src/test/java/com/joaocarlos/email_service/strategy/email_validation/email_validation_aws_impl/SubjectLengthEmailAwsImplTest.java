package com.joaocarlos.email_service.strategy.email_validation.email_validation_aws_impl;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class SubjectLengthEmailAwsImplTest {
    private SubjectLengthEmailAwsImpl subjectLengthEmailAwsImpl;

    @BeforeEach
    public void setup() {
        subjectLengthEmailAwsImpl = new SubjectLengthEmailAwsImpl();
    }

    @Test
    public void execute_WithValidEmailDTO_DoesNotAnyThrowsException() {
        assertDoesNotThrow(() -> subjectLengthEmailAwsImpl.execute(EMAIL_DTO));
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

        assertThrowsExactly(EmailServiceException.class, () -> subjectLengthEmailAwsImpl.execute(emailDTO));
    }

    @Test
    public void execute_WithSubjectLengthExactly45_DoesNotThrowException() {
        String subject = "a".repeat(120);
        EmailDTO emailDTO = new EmailDTO(
                "email_destinariario@email.com",
                "email_remetente@email.com",
                "Nome destinatario",
                subject,
                "Conteudo email"
        );

        assertDoesNotThrow(() -> subjectLengthEmailAwsImpl.execute(emailDTO));
    }

    @Test
    public void getEmailServiceName_ReturnsEmailServiceName() {
        String sut = subjectLengthEmailAwsImpl.getEmailServiceName();

        assertThat(sut).isNotBlank();
        assertThat(sut).isEqualTo("AWS");
    }
}
