# 📚 Digital Library Platform
> **Sistema completo de biblioteca digital - API Backend**  
> OAuth2 • Docker • RabbitMQ • Google Books API

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Containerized-blue.svg)](https://www.docker.com/)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Events-ff6600.svg)](https://www.rabbitmq.com/)

## 🚀 **Features Principais**
- ✅ **Autenticação OAuth2/JWT** - Sistema completo de login e autorização
- ✅ **Containerização Docker** - Deploy simplificado com Docker Compose  
- ✅ **Eventos Assíncronos** - RabbitMQ para auditoria automática
- ✅ **Google Books API** - Integração para busca automática de livros
- ✅ **Sistema de Roles** - ADMIN, LIBRARIAN, USER com permissões granulares
- ✅ **API RESTful** - Documentação Swagger integrada

## 🛠️ **Tech Stack**
### **Backend**
- **Java 17** - Linguagem principal
- **Spring Boot 3.x** - Framework REST
- **Spring Security** - OAuth2/JWT
- **Spring Data JPA/Hibernate** - ORM e persistência
- **MySQL** - Banco de dados

### **Integrações & Tools**
- **Docker & Docker Compose** - Containerização
- **RabbitMQ** - Message broker para eventos
- **Google Books API** - Busca automática de livros
- **Swagger/OpenAPI** - Documentação da API

## 📊 **Sistema de Roles & Permissões**
| Role | Gerenciar Livros | Empréstimos | Usuários | Relatórios |
|------|:----------------:|:-----------:|:--------:|:----------:|
| **ADMIN** | ✅ | ✅ | ✅ | ✅ |
| **LIBRARIAN** | ✅ | ✅ | ❌ | ✅ |
| **USER** | ❌ | Ver próprios | ❌ | ❌ |

## 🔗 **Projetos Relacionados**
- **[Interface Web](https://isaacnogueir.github.io/biblioHub/)** - Frontend completo HTML/CSS/JS

---
## 👨‍💻 **Desenvolvedor**
**Isaac Nogueira**  
[![LinkedIn](https://img.shields.io/badge/-LinkedIn-blue?style=flat-square&logo=Linkedin&logoColor=white)](https://linkedin.com/in/isaacnogueir)
[![GitHub](https://img.shields.io/badge/-GitHub-black?style=flat-square&logo=github)](https://github.com/isaacnogueir)
