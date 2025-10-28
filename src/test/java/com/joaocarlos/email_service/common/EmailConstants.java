package com.joaocarlos.email_service.common;

import com.joaocarlos.email_service.dto.EmailDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

public class EmailConstants {
    public static final EmailDTO EMAIL_DTO = new EmailDTO("email_destinatario@email.com", "email_remetente@email.com", "Nome Destinatário", "Assunto email", "Conteudo email");

    public static final List<Arguments> INVALID_EMAILS = List.of(
            Arguments.of(
                    new EmailDTO(
                            "email_destinariario_com_mais_45_caracteres@email.com",
                            "email_remetente@email.com",
                            "Nome Destinatário",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            "email_destinatario@email.com",
                            "email_remetente_com_mais_45_caracteres@email.com",
                            "Nome Destinatário",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            "email_destinatario@email.com",
                            "email_remetente@email.com",
                            "Nome Destinatário",
                            "Esse texto contem mais de 120 caracteres, e estou escrevendo para testar validacao de um campo que exige um tamanho minimo maior, apenas para garantir que o sistema consiga identificar corretamente quando o limite é atingido.",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            "email_destinatario@email.com",
                            "email_remetente@email.com",
                            "Nome Destinatário",
                            "Assunto email",
                            "Esse texto contém mais de 256 caracteres, e estou escrevendo para testar validação de um campo que exige um tamanho mínimo maior, garantindo que o sistema consiga reconhecer corretamente os limites definidos e tratar o excesso de forma adequada, evitando falhas e comportamentos inesperados durante o processamento dos dados inseridos pelo usuário. Além disso, esse teste ajuda a assegurar a integridade das informações e a robustez da aplicação em diferentes cenários de uso.")
            ),
            Arguments.of(
                    new EmailDTO(
                            "email_destinatario@email.com",
                            "email_remetente@email.com",
                            "Nome de uma pessoa com mais de 60 caracteres para testar validações de comprimento",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            "",
                            "email_remetente@email.com",
                            "Nome Destinatário",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            "email_destinatario@email.com",
                            "",
                            "Nome Destinatário",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            null,
                            "email_remetente@email.com",
                            "Nome Destinatário",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            "email_destinatario@email.com",
                            null,
                            "Nome Destinatário",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            "email_destinatario",
                            "email_remetente@email.com",
                            "Nome Destinatário",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            "email_destinatario@email.com",
                            "email_remetente",
                            "Nome Destinatário",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            "email_destinatario@email.com",
                            "email_remetente@email.com",
                            "Nome Destinatário 01",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            "email_destinatario@email.com",
                            "email_remetente@email.com",
                            "Nome Destinatário 01",
                            "Assunto email",
                            "Conteudo email")
            ),
            Arguments.of(
                    new EmailDTO(
                            null,
                            null,
                            null,
                            null,
                            null)
            ),
            Arguments.of(
                    new EmailDTO(
                            "",
                            "",
                            "",
                            "",
                            "")
            )
    );
}
