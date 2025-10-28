package com.joaocarlos.email_service.email;

import com.joaocarlos.email_service.dto.EmailDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static com.joaocarlos.email_service.common.EmailConstants.INVALID_EMAILS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void sendEmail_WithValidData_ReturnsNoContent(){
        ResponseEntity<Void> sut = restTemplate.postForEntity("/api/v1/email", EMAIL_DTO, Void.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @ParameterizedTest
    @MethodSource("providersInvalidEmails")
    public void sendEmail_WhithInvalidEmailDTO_ReturnsUnprocessableEntity(EmailDTO emailDTO) {
        ResponseEntity<Void> sut = restTemplate.postForEntity("/api/v1/email", emailDTO, Void.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void sendEmail_WithSameEmail_ReturnsInternalServerError(){
        EmailDTO emailDTO = new EmailDTO("email_destinatario@email.com", "email_destinatario@email.com", "Nome Destinatário", "Assunto email", "Conteudo email");
        ResponseEntity<Void> sut = restTemplate.postForEntity("/api/v1/email", emailDTO, Void.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private static Stream<Arguments> providersInvalidEmails() {
        return INVALID_EMAILS.stream();
    }
}
