package com.joaocarlos.email_service.factory;

import com.joaocarlos.email_service.exceptions.InvalidEmailStrategyException;
import com.joaocarlos.email_service.strategy.email_validation.EmailValidationStrategy;
import com.joaocarlos.email_service.strategy.email_validation.factory.EmailValidationFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailValidationFactoryTest {
    private EmailValidationFactory emailValidationFactory;
    @Mock
    private EmailValidationStrategy emailValidationStrategy;

    @BeforeEach
    public void setUp() {
        when(emailValidationStrategy.getEmailServiceName()).thenReturn("AWS");
        emailValidationFactory = new EmailValidationFactory(Set.of(emailValidationStrategy));
    }

    @Test
    public void getEmailValidationStrategy_ReturnsCorrectEmailValidatorsStrategy() {
        List<EmailValidationStrategy> strategies = emailValidationFactory.getEmailValidationStrategy("AWS");
        List<EmailValidationStrategy> awsStrategies = List.of(emailValidationStrategy);

        assertNotNull(strategies);
        assertEquals(strategies, awsStrategies);
        verify(emailValidationStrategy).getEmailServiceName();
    }

    @Test
    public void getEmailValidationStrategy_InvalidNameServiceEmail_ThrowsException() {
        assertThrowsExactly(InvalidEmailStrategyException.class, () -> emailValidationFactory.getEmailValidationStrategy("invalid"));
    }
}
