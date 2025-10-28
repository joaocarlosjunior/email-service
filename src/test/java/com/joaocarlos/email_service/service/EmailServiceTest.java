package com.joaocarlos.email_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.InvalidEmailStrategyException;
import com.joaocarlos.email_service.exceptions.SameEmailException;
import com.joaocarlos.email_service.exceptions.SerializeJsonException;
import com.joaocarlos.email_service.strategy.email_service.EmailServiceStrategy;
import com.joaocarlos.email_service.strategy.email_service.factory.EmailServiceFactory;
import com.joaocarlos.email_service.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;
    @Mock
    private EmailServiceFactory emailServiceFactory;
    @Mock
    private EmailServiceStrategy emailServiceStrategy;

    @Test
    public void sendEmail_WithCorrectStrategy_DoesNotThrowException() throws Exception {
        TestUtils.setField(emailService, "EMAIL_SERVICE", "AWS");
        when(emailServiceFactory.getEmailStrategy("AWS")).thenReturn(emailServiceStrategy);

        emailService.sendEmail(EMAIL_DTO);

        verify(emailServiceFactory).getEmailStrategy("AWS");
        verify(emailServiceStrategy).sendEmail(EMAIL_DTO);
    }

    @Test
    public void sendEmail_WithIInvalidNameServiceEmail_ThrowsException() {
        TestUtils.setField(emailService, "EMAIL_SERVICE", "TEST");
        when(emailServiceFactory.getEmailStrategy("TEST"))
                .thenThrow(new InvalidEmailStrategyException("Invalid name service email: TEST"));

        assertThrowsExactly(InvalidEmailStrategyException.class, () -> emailService.sendEmail(EMAIL_DTO));
    }

    @Test
    public void sendEmail_WithSameEmails_ThrowsException() {
        EmailDTO emailDTO = new EmailDTO("email_destinatario@email.com", "email_destinatario@email.com", "Nome Destinatário", "Assunto email", "Conteudo email");
        assertThrowsExactly(SameEmailException.class, () -> emailService.sendEmail(emailDTO));
    }

    @Test
    public void sendEmail_WhenJsonProcessingFails_ThrowsException() throws Exception {
        TestUtils.setField(emailService, "EMAIL_SERVICE", "AWS");
        when(emailServiceFactory.getEmailStrategy("AWS")).thenReturn(emailServiceStrategy);
        doThrow(JsonProcessingException.class).when(emailServiceStrategy).sendEmail(EMAIL_DTO);

        assertThrowsExactly(SerializeJsonException.class, () -> emailService.sendEmail(EMAIL_DTO));
    }
}
