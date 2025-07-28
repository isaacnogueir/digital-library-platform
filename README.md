#  Digital Library Platform

> Sistema de gerenciamento de biblioteca digital com autenticação OAuth2 e controle de acesso baseado em roles, ainda em desenvolvimento.

## Sobre o Projeto

O **Digital Library Platform** é uma API REST em desenvolvimento que usa Spring Boot para gerenciamento completo de bibliotecas digitais. O sistema oferece autenticação segura, controle de acesso granular e funcionalidades completas para administração de acervo e usuários.

##  Principais Funcionalidades

-  **Autenticação OAuth2** com JWT
-  **Sistema de Roles** (Admin, Librarian, User)
-  **Gerenciamento de Livros** e acervo
-  **Controle de Bibliotecas** múltiplas
-  **Gestão de Usuários** e perfis
-  **Dashboard** administrativo
-  **Sistema de Busca** avançado
-  **Relatórios** e estatísticas

## Tecnologias Utilizadas

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security** (OAuth2 + JWT)
- **Spring Data JPA**
- **MySQL** / PostgreSQL
- **Maven**

### Arquitetura
- **REST API**
- **Microserviços** ready
- **Clean Architecture**
- **Design Patterns**

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+ ou PostgreSQL 12+
- IDE (IntelliJ IDEA recomendado)

##  Documentação da API

### Endpoints Principais

#### Autenticação
```http
POST /auth/login          # Login do usuário
POST /auth/register       # Registro de novo usuário
POST /auth/refresh        # Renovar token JWT
```

#### Usuários
```http
GET    /api/users         # Listar usuários
GET    /api/users/{id}    # Buscar usuário por ID
POST   /api/users         # Criar novo usuário
PUT    /api/users/{id}    # Atualizar usuário
DELETE /api/users/{id}    # Remover usuário
```

#### Livros
```http
GET    /api/books         # Listar livros
GET    /api/books/{id}    # Buscar livro por ID
POST   /api/books         # Cadastrar novo livro
PUT    /api/books/{id}    # Atualizar livro
DELETE /api/books/{id}    # Remover livro
GET    /api/books/search  # Buscar livros
```

#### Bibliotecas
```http
GET    /api/libraries     # Listar bibliotecas
POST   /api/libraries     # Criar nova biblioteca
PUT    /api/libraries/{id} # Atualizar biblioteca
```

## Sistema de Permissões

### Roles Disponíveis

| Role | Descrição | Permissões |
|------|-----------|------------|
| `ADMIN` | Administrador do sistema | Acesso total |
| `LIBRARIAN` | Bibliotecário | Gestão de livros e usuários |
| `USER` | Usuário comum | Consulta e empréstimos |

### Exemplo de Uso
```java
@PreAuthorize("hasRole('LIBRARIAN')")
@GetMapping("/books/manage")
public ResponseEntity<List<Book>> manageBooks() {
    // Apenas bibliotecários podem acessar
}
```
## Estrutura do Projeto
```
src/
├── main/
│   ├── java/com/project2025/auth_2/
│   │   ├── config/          # Configurações
│   │   ├── controller/      # Controladores REST
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # Entidades JPA
│   │   ├── repository/     # Repositórios
│   │   ├── service/        # Serviços de negócio
│   │   └── security/       # Configurações de segurança
│   └── resources/
│       ├── application.yml # Configurações
│       └── data.sql       # Dados iniciais
```

##  Roadmap

- [ ] Interface web (React/Angular)
- [ ] Notificações em tempo real
- [ ] Integração com APIs externas
- [ ] Sistema de recomendações
- [ ] App mobile
- [ ] Relatórios avançados
## Status do Projeto

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Version](https://img.shields.io/badge/version-1.0.0-blue)
![License](https://img.shields.io/badge/license-MIT-green)

## Contato

**Isaac Nogueira**
- Email: [seu-email@email.com]
- LinkedIn: [Isaac Nogueira](https://www.linkedin.com/in/isaacferreiran)
- GitHub: [@isaacnogueir](https://github.com/isaacnogueir)

---

**Se este projeto te ajudou, deixe uma estrela!**

**Compartilhe com outros desenvolvedores!**

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---
*Desenvolvido com por Isaac Nogueira*
