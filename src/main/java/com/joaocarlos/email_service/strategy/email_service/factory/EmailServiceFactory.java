package com.joaocarlos.email_service.strategy.email_service.factory;

import com.joaocarlos.email_service.exceptions.InvalidEmailStrategyException;
import com.joaocarlos.email_service.strategy.email_service.EmailServiceStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class EmailServiceFactory {
    private final Map<String, EmailServiceStrategy> strategies = new HashMap<>();

    public EmailServiceFactory(Set<EmailServiceStrategy> emailStrategies) {
        emailStrategies.forEach(emailStrategy -> {
            strategies.put(emailStrategy.getNameServiceEmail(), emailStrategy);
        });
    }

    public EmailServiceStrategy getEmailStrategy(String name) {
        EmailServiceStrategy emailServiceStrategy = strategies.get(name);
        if (emailServiceStrategy == null) {
            throw new InvalidEmailStrategyException("Invalid name service email: " + name);
        }
        return emailServiceStrategy;
    }
}
