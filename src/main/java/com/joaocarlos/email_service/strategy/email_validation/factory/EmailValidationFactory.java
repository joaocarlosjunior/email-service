package com.joaocarlos.email_service.strategy.email_validation.factory;

import com.joaocarlos.email_service.exceptions.InvalidEmailStrategyException;
import com.joaocarlos.email_service.strategy.email_validation.EmailValidationStrategy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EmailValidationFactory {
    private final Map<String, List<EmailValidationStrategy>> strategies = new HashMap<>();

    public EmailValidationFactory(Set<EmailValidationStrategy> emailValidationStrategies) {
        emailValidationStrategies.forEach(emailValidationStrategy -> {
            strategies.computeIfAbsent(emailValidationStrategy.getEmailServiceName(), k -> new ArrayList<>()).add(emailValidationStrategy);
        });
    }

    public List<EmailValidationStrategy> getEmailValidationStrategy(String name) {
        List<EmailValidationStrategy> emailValidationStrategies = strategies.get(name);
        if (emailValidationStrategies == null) {
            throw new InvalidEmailStrategyException("No email validation strategy found for name: " + name);
        }
        return emailValidationStrategies;
    }
}
