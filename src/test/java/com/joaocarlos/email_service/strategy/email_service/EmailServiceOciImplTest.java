package com.joaocarlos.email_service.strategy.email_service;

import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.exceptions.EmailServiceException;
import com.joaocarlos.email_service.strategy.email_service.impl.EmailServiceOciImpl;
import com.joaocarlos.email_service.strategy.email_validation.EmailValidationStrategy;
import com.joaocarlos.email_service.strategy.email_validation.factory.EmailValidationFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static com.joaocarlos.email_service.common.EmailConstants.INVALID_EMAILS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceOciImplTest {
    @InjectMocks
    private EmailServiceOciImpl emailAwsImpl;
    @Mock
    private EmailValidationFactory emailValidationFactory;
    @Mock
    private EmailValidationStrategy emailValidationStrategy;

    @Test
    void getNameServiceEmail_ReturnsNameServiceEmail() {
        assertEquals("OCI", emailAwsImpl.getNameServiceEmail());
    }

    @Test
    void sendEmail_WithEmailDTONull_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> emailAwsImpl.sendEmail(null));
    }

    @Test
    void sendEmail_WithValidEmailDTO_DoesNotThrowException() {
        when(emailValidationFactory.getEmailValidationStrategy("OCI")).thenReturn(List.of(emailValidationStrategy));

        assertDoesNotThrow(() -> emailAwsImpl.sendEmail(EMAIL_DTO));
    }

    @ParameterizedTest
    @MethodSource("providersInvalidEmails")
    public void sendEmail_WhithInvalidEmailDTO_ThrowsException(EmailDTO emailDTO) {
        when(emailValidationFactory.getEmailValidationStrategy("OCI")).thenReturn(List.of(emailValidationStrategy));
        doThrow(EmailServiceException.class).when(emailValidationStrategy).execute(emailDTO);

        assertThrowsExactly(EmailServiceException.class, () -> emailAwsImpl.sendEmail(emailDTO));
    }

    private static Stream<Arguments> providersInvalidEmails() {
        return INVALID_EMAILS.stream();
    }
}
