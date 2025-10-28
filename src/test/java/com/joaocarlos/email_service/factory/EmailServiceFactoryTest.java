package com.joaocarlos.email_service.factory;

import com.joaocarlos.email_service.exceptions.InvalidEmailStrategyException;
import com.joaocarlos.email_service.strategy.email_service.EmailServiceStrategy;
import com.joaocarlos.email_service.strategy.email_service.factory.EmailServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceFactoryTest {
    private EmailServiceFactory emailServiceFactory;
    @Mock
    private EmailServiceStrategy awsStrategy;
    @Mock
    private EmailServiceStrategy ociStrategy;

    @BeforeEach
    public void setUp() {
        when(awsStrategy.getNameServiceEmail()).thenReturn("AWS");
        when(ociStrategy.getNameServiceEmail()).thenReturn("OCI");

        emailServiceFactory = new EmailServiceFactory(Set.of(awsStrategy, ociStrategy));
    }

    @Test
    public void getEmailStrategy_ReturnsCorrectAwsStrategy() {
        EmailServiceStrategy strategy = emailServiceFactory.getEmailStrategy("AWS");

        assertNotNull(strategy);
        assertEquals(strategy, awsStrategy);
        verify(awsStrategy).getNameServiceEmail();
    }

    @Test
    public void getEmailStrategy_ReturnsCorrectOciStrategy() {
        EmailServiceStrategy strategy = emailServiceFactory.getEmailStrategy("OCI");

        assertNotNull(strategy);
        assertEquals(strategy, ociStrategy);
        verify(ociStrategy).getNameServiceEmail();
    }

    @Test
    public void getEmailStrategy_InvalidNameServiceEmail_ThrowsException() {
        assertThrows(InvalidEmailStrategyException.class, () -> emailServiceFactory.getEmailStrategy("invalid"));
    }
}


