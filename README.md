

![Status: Concluído](https://img.shields.io/badge/status-concluído-green)
![Java](https://img.shields.io/badge/Java-17-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?logo=spring)
![Oracle DB](https://img.shields.io/badge/Oracle-Database-red?logo=oracle)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Frontend-green?logo=thymeleaf)



---
link do video: [https://youtu.be/2Ol0obkLBjc](https://www.youtube.com/watch?v=jU4t4XYTd44)
## 🧑‍💻 Autores

<div align="center">

| Nome | RM |
| :--- | :--- |
| **Vinicius Murtinho Vicente** | 551151 |
| **Lucas Barreto Consentino** | 557107 |
| **Gustavo Bispo Cordeiro** | 558515 |

</div>

## ✨ Funcionalidades Spring Security

Autenticação & Autorização com Spring Security usando JWT.
Criptografia de senha.
Cadastro de usuários com roles (ADMIN e USUARIO).
CRUD básico de usuários.
Proteção de endpoints por roles (ROLE_ADMIN e ROLE_USER).
Login via front-end com senha cifrada.
Endpoint para fornecer chave pública RSA para o front-end.
Geração de token JWT para autenticação em endpoints protegidos.

---




## ✨ Funcionalidades Principais

Autenticação & Autorização com Spring Security.

🔐 Autenticação & Autorização

Login com JWT (Auth0)
Servidor OAuth2 Authorization Server
Controle de acesso com Spring Security
Perfis: ROLE_USER e ROLE_ADMIN

Senhas criptografadas com BCrypt
---

## Módulos do Sistema
Cadastro, listagem, edição e exclusão (CRUD) de:

Usuários
Currículos
Admin

## Validações de Negócio

Campos únicos
Regras aplicadas no Service Layer
Validação com Jakarta Validation

##Frontend & Dashboard

Interface em Thymeleaf
Integração com segurança do Spring
Páginas protegidas
Layout dinâmico

## 🏗️ Arquitetura do Sistema
A aplicação segue padrão Layered Architecture:

Frontend (Thymeleaf)
            |
Controller Layer (Endpoints REST + Views)
            |
Service Layer (Regras de Negócio)
            |
Repository Layer (Spring Data JPA)
            |
Database (Oracle SQL)

## Detalhes de Segurança

Autenticação JWT
OAuth2 Authorization Server
Criptografia BCrypt
Controle de rotas baseado em Roles
Thymeleaf integrado ao Spring Security
Proteção de páginas e sessões
Testes com spring-security-test

---

## Tecnologias Utilizadas

*Backend

Java 17
Spring Boot 3.5.7
Spring Web
Spring Data JPA
Spring Validation
Spring Security
OAuth2 Authorization Server
Lombok
JWT (Auth0 java-jwt)

*Frontend
Thymeleaf


*Banco & Ferramentas

Oracle Database
Maven
Spring Boot DevTools

---

## Como Executar Localmente
Pré-requisitos

Java JDK 17
Maven
Oracle Database rodando
Git instalado

---
1️⃣ Clonar o Repositório

git clone [https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git](https://github.com/VMurtis/JAVA-GS.git)


---

2️⃣ Executar a Aplicação
mvn spring-boot:run

---

## Estrutura do Projeto

src/main/java/br/com/fiap/gs/
 ├─ controllers/
 ├─ dto/
 ├─ entities/
 ├─ repository/
 ├─ security/
 ├─ services/
 └─ GSApplication.java

src/main/resources/
 ├─ static/
 ├─ templates/
 └─ application.properties

## Dependências

spring-boot-starter-web
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-starter-oauth2-authorization-server
spring-boot-starter-thymeleaf
spring-boot-starter-validation
lombok
java-jwt

ojdbc11


