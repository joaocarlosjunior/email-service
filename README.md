# 📧 Email Service - Desafio Back-end Viasoft

## 📝 Descrição do Projeto

Este projeto foi desenvolvido como parte do **teste técnico para a vaga de Desenvolvedor Back-end Java Pleno na Viasoft**.  
O objetivo principal é criar uma aplicação REST capaz de processar requisições para envio de e-mails, adaptando dinamicamente o formato dos dados conforme o serviço configurado(**AWS** ou **OCI**) sem necessidade de alterar o objeto de entrada nem reiniciar a aplicação.

A aplicação foi construída em **Spring Boot 3**, utilizando princípios de **arquitetura em camadas**, **boas práticas de código limpo (Clean Code)**, **padrões de projeto (Design Patterns)** e **testes automatizados** em múltiplos níveis (unitário, integração e end-to-end).

Descrição do Desafio: [Desafio Back-end Viasoft.pdf](https://github.com/joaocarlosjunior/email-service/blob/main/Desafio%20Back-end%20Viasoft.pdf)

---

## 🎯 Objetivo do Desafio (Conforme PDF da Viasoft)

- Criar uma **API REST** com um endpoint que recebe um objeto com informações de e-mail.
- A API deve adaptar o objeto para **EmailAwsDTO** ou **EmailOciDTO** conforme o valor configurado em `application.yml`.
- Serializar o objeto em **JSON** e imprimir no console.
- Retornar **204 (No Content)** em caso de sucesso.
- Tratar erros com **400 (Bad Request)** ou **500 (Internal Server Error)**.
- Aplicar **validações de tamanho máximo** nos campos, conforme as regras abaixo:

| Campo | AWS | OCI |
|--------|------|------|
| recipient / recipientEmail | 45 | 40 |
| recipientName | 60 | 50 |
| sender / senderEmail | 45 | 40 |
| subject | 120 | 100 |
| content / body | 256 | 250 |

---

## ⚙️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Web**
- **Spring Validation**
- **Spring Cloud Config**
- **Spring Actuator**
- **MapStruct 1.6.3**
- **JUnit 5 / Failsafe Plugin**
- **JaCoCo** (análise de cobertura)
- **PIT Mutation Testing** (teste de mutação)
- **Maven** como gerenciador de dependências

---

## 🧩 Arquitetura e Estrutura do Projeto

O projeto foi organizado com uma arquitetura em camadas e modular:
````
com.joaocarlos.email_service
├── controller # Endpoints REST
├── dto # Objetos de transferência de dados (EmailDTO, etc)
├── exceptions # Tratamento e padronização de erros
├── factory # Implementação do Factory Pattern
├── mappers # Conversões automáticas via MapStruct
├── strategy # Estratégias de envio e validação de e-mails
│ ├── impl # Implementações concretas (AWS, OCI)
├── service # Camada de negócios
└── tests # Testes unitários, integração e E2E
````

---

## 🧠 Padrões de Projeto Utilizados

### 🔹 **Strategy Pattern**

Usado para **encapsular comportamentos variáveis** de envio e validação de e-mails.

- **EmailServiceStrategy** → Define a lógica para envio de e-mail, de acordo com serviço(AWS ou OCI) que estiver configurado em `application.yml`.
- **EmailValidationStrategy** → Realiza as validações de acordo com as regras específicas do serviço selecionado.

### 🔹 **Factory Pattern**

Responsável por **instanciar dinamicamente** as estratégias corretas conforme o contexto.

- **EmailServiceFactory** → Cria a instância da estratégia de envio (`AWS` ou `OCI`).
- **EmailValidationFactory** → Cria a instância da estratégia de validação correspondente.

---

## 🔧 Servidor de Configuração (Spring Cloud Config)

Uma das abordagens diferenciais adotadas foi o desenvolvimento de um **servidor de configuração**, que permite **alterar o serviço de e-mail (AWS ou OCI)** **sem precisar reiniciar a aplicação principal**.

Exemplo de configuração no `application.yml`:

```yaml
spring:
  application:
    name: email-service
  config:
    import: configserver:http://localhost:8888

email:
  service: AWS  # Pode ser alterado para OCI dinamicamente
```

Ao atualizar o valor e executar o endpoint /actuator/refresh, a aplicação passa automaticamente a utilizar o novo serviço.

## 🧪 Estratégia de Testes

O projeto foi desenvolvido com foco em qualidade de código e cobertura de testes, contemplando os seguintes níveis:

| Tipo de Teste           | Framework             | Objetivo                                   |
| ----------------------- | --------------------- | ------------------------------------------ |
| **Unitário**            | JUnit 5               | Testar componentes isoladamente            |
| **Integração**          | Maven Failsafe Plugin | Garantir o funcionamento entre camadas     |
| **End-to-End (E2E)**    | Spring Boot Test      | Validar o fluxo completo da aplicação      |
| **Cobertura de Código** | JaCoCo                | Medir a cobertura total de testes          |
| **Teste de Mutação**    | PIT Mutation Testing  | Verificar a eficácia dos testes existentes |


## 📊 Relatórios de Qualidade
### 📈 JaCoCo Report
Gera o relatório de cobertura de testes de código.

Relatório JaCoCo:

<img width="1912" height="413" alt="Image" src="https://github.com/user-attachments/assets/f49100df-e732-4932-95f6-7943a781494c" />

### 🧬 PIT Mutation Report

Executa testes de mutação para medir a robustez dos testes.

Relatório PITest:

<img width="1910" height="644" alt="Image" src="https://github.com/user-attachments/assets/46b6e178-1081-46d2-abb8-5b95fabda646" />

#### 🧬 Observações sobre o Relatório do PIT

O relatório do **PIT Mutation Testing** atingiu quase 100% de cobertura de mutação, com apenas um mutante sobrevivente.

A mutação que sobreviveu refere-se à **remoção da chamada `System.out.println(json)`** nas classes
`EmailServiceAwsImpl` e `EmailServiceOciImpl`, linhas 36 e 37, respectivamente:

```java
System.out.println(json);
```

Essa linha foi exigida na descrição do teste técnico para exibir o e-mail serializado em JSON no console.
No entanto, como essa ação não altera o comportamento funcional da aplicação e não é coberta por testes (por se tratar apenas de uma saída no console), o PIT considera o mutante como “sobrevivente”.

✅ Importante: isso não representa uma falha de teste, mas sim um comportamento esperado do PIT, já que a impressão no console não é uma lógica testável nem afeta o resultado da aplicação.

## 🚀 Executando a Aplicação

1. Clonar o repositório
    ```
    git clone https://github.com/joaocarlosjunior/email-service.git
    cd email-service
    ```
2. Executar a aplicação:
    - Windows:

    ```
    mvnw.cmd spring-boot:run
    ```

    - Linux:

    ```
    ./mvnw spring-boot:run
    ```

## 🔥 Testando o Endpoint
#### POST /email/send
```json
{
  "recipient": "destinatario@email.com",
  "recipientName": "Nome destinatário",
  "sender": "remetente@email.com",
  "subject": "Assunto do e-mail",
  "content": "Conteúdo do e-mail"
}
```
#### Exemplo de resposta:

- 204 No Content → Sucesso

- 400 Bad Request → Erro de validação

- 500 Internal Server Error → Erro interno da aplicação

## 🧰 Executando os Testes

- Testes Unitários
    ```
    ./mvnw clean test
    ```

- Testes End-to-End
    ```
    ./mvnw clean verify -Dsurefire.skip=true
    ```

- Gerar relatório de cobertura (JaCoCo)
    ```
    ./mvnw clean test jacoco:report
    ```

- Executar teste de mutação (PITest)
    ```
    ./mvnw test-compile org.pitest:pitest-maven:mutationCoverage
    ```

## 💡 Diferenciais Técnicos Implementados

✅ Desenvolvimento orientado a testes (TDD).

✅ Utilização de Factory e Strategy Patterns.

✅ Integração com Spring Cloud Config para troca dinâmica de serviço.

✅ Testes de mutação com PITest para garantir qualidade real dos testes.

✅ Cobertura completa analisada com JaCoCo.

✅ Tratamento de erros e mensagens padronizadas.

## 👨‍💻 Autor

João Carlos Junior

Desenvolvedor Full-stack

📎 [GitHub](https://github.com/joaocarlosjunior)

📎 [Linkedin](https://www.linkedin.com/in/joaocarlosjr/)
