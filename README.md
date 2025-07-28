# Digital Library Platform

> Sistema completo de gerenciamento de biblioteca digital com autenticação OAuth2, controle de usuários, livros e empréstimos.

## Sobre o Projeto

O **Digital Library Platform** é uma API REST robusta desenvolvida em Spring Boot para gerenciamento completo de bibliotecas digitais. O sistema oferece autenticação segura com JWT, controle de acesso baseado em roles (ADMIN, LIBRARIAN, USER) e funcionalidades completas para administração de usuários, livros e empréstimos.

## Principais Funcionalidades

- **Autenticação OAuth2** - Sistema completo de login/registro com JWT
- **Gestão de Usuários** - CRUD completo com controle de perfis e roles
- **Gerenciamento de Livros** - Cadastro, busca e controle de disponibilidade
- **Sistema de Empréstimos** - Controle completo de empréstimos e devoluções
- **Controle de Acesso** - Permissões baseadas em roles (ADMIN/LIBRARIAN/USER)
- **Documentação Swagger** - API totalmente documentada
- **Validações Avançadas** - Bean Validation em todos os endpoints

## Tecnologias Utilizadas

### Backend
- **Java 17** - Linguagem principal
- **Spring Boot 3.x** - Framework principal
- **Spring Security** - Autenticação e autorização OAuth2/JWT
- **Spring Data JPA** - Persistência de dados
- **MySQL/PostgreSQL** - Banco de dados relacional
- **Swagger/OpenAPI** - Documentação da API
- **Bean Validation** - Validação de dados
- **Maven** - Gerenciamento de dependências

### Ferramentas
- **Maven 3.6+**
- **IntelliJ IDEA** (recomendado)
- **Postman/Insomnia** - Testes de API

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+ ou PostgreSQL 12+
- IDE (IntelliJ IDEA recomendado)

## Documentação da API

### Swagger UI
Acesse a documentação interativa em: `http://localhost:8080/swagger-ui.html`

### Endpoints Principais

#### Autenticação
```http
POST   /auth/register        # Registrar novo usuário
POST   /auth/login           # Login e obtenção do token JWT
```

#### Gestão de Usuários
```http
GET    /api/users/profile    # Perfil do usuário logado
PUT    /api/users/users/{id} # Atualizar perfil
GET    /api/users/{id}       # Buscar usuário por ID
GET    /api/users/login/{login} # Buscar por login
GET    /api/users/list       # Listar todos os usuários
GET    /api/users/active     # Listar usuários ativos
GET    /api/users/role/{role} # Buscar por role
PATCH  /api/users/{id}/activate   # Ativar usuário
PATCH  /api/users/{id}/deactivate # Desativar usuário
```

#### Gerenciamento de Livros
```http
POST   /api/books/create     # Criar novo livro
PUT    /api/books/update/{id} # Atualizar livro
GET    /api/books/{id}       # Buscar por ID
GET    /api/books/isbn/{isbn} # Buscar por ISBN
GET    /api/books/title/{title} # Buscar por título
GET    /api/books/list       # Listar todos os livros
GET    /api/books/available  # Listar livros disponíveis
```

#### Sistema de Empréstimos
```http
POST   /api/loans/register   # Criar novo empréstimo
PUT    /api/loans/return/{id} # Devolver livro
PUT    /api/loans/cancel/{id} # Cancelar empréstimo
GET    /api/loans/actives    # Listar empréstimos ativos
GET    /api/loans/findOver   # Listar empréstimos vencidos
GET    /api/loans/LoansByUser/{id} # Empréstimos por usuário
GET    /api/loans/findByUser/{id}  # Buscar empréstimo por ID
```

### Exemplos de Payload

#### Registro de Usuário
```json
{
  "login": "joao.silva",
  "email": "joao@email.com",
  "password": "senha123",
  "name": "João Silva",
  "role": "USER"
}
```

#### Login
```json
{
  "login": "joao.silva",
  "password": "senha123"
}
```

#### Criação de Livro
```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "publisher": "Prentice Hall",
  "publicationYear": 2008,
  "category": "PROGRAMMING",
  "totalCopies": 5
}
```

## Sistema de Permissões

### Roles Disponíveis

| Role | Descrição | Permissões |
|------|-----------|------------|
| `ADMIN` | Administrador do sistema | Acesso total a todas as funcionalidades |
| `LIBRARIAN` | Bibliotecário | Gestão de livros, usuários e empréstimos |
| `USER` | Usuário comum | Consulta de livros e perfil próprio |

### Matriz de Permissões

| Funcionalidade | ADMIN | LIBRARIAN | USER |
|---------------|-------|-----------|------|
| Criar/Editar Livros | ✅ | ❌ | ❌ |
| Visualizar Livros | ✅ | ✅ | ❌ |
| Gerenciar Empréstimos | ✅ | ✅ | ❌ |
| Gerenciar Usuários | ✅ | ❌ | ❌ |
| Ver Próprio Perfil | ✅ | ✅ | ✅ |

## Autenticação JWT

### Como usar
1. Faça login em `/auth/login` para obter o token
2. Inclua o token no header das requisições:
```http
Authorization: Bearer seu_token_jwt_aqui
```

### Exemplo de resposta do login
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600,
  "tokenType": "Bearer",
  "user": {
    "id": 1,
    "login": "joao.silva",
    "email": "joao@email.com",
    "role": "USER"
  }
}
```

### Funcionalidades Implementadas
- Sistema completo de autenticação OAuth2/JWT
- CRUD completo de usuários com controle de roles
- Gestão de livros com busca por ID, ISBN e título
- Sistema de empréstimos com controle de devoluções
- Controle de livros vencidos
- Documentação Swagger integrada
- Validação de dados em todos os endpoints
- Controle de acesso granular por role

### Fluxo de Uso
1. Registre um usuário em `/auth/register`
2. Faça login em `/auth/login` para obter o token
3. Use o token para acessar os endpoints protegidos
4. Gerencie livros, usuários e empréstimos conforme suas permissões

## Roadmap

### Versão 1.0 (Atual)
- Sistema de autenticação OAuth2/JWT
- CRUD completo de usuários, livros e empréstimos
- Controle de acesso por roles
- Documentação Swagger

### Versão 1.1 (Próximas melhorias)
- Interface web 
- Notificações de empréstimos vencidos
- Sistema de reservas
- Relatórios e dashboards

### Versão 2.0 (Futuro)
- App mobile
- Integração com APIs externas (Google Books)
- Sistema de recomendações
- Chat interno
- Backup automático

## Status do Projeto

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Version](https://img.shields.io/badge/version-1.0.0-blue)
![License](https://img.shields.io/badge/license-MIT-green)
![Java](https://img.shields.io/badge/java-17-orange)
![Spring Boot](https://img.shields.io/badge/spring%20boot-3.x-brightgreen)

## Contato

**Isaac Nogueira**

- LinkedIn: [Isaac Nogueira](https://www.linkedin.com/in/isaacferreiran/)
- GitHub: [@isaacnogueir](https://github.com/isaacnogueir)

---

**Se este projeto te ajudou, deixe uma estrela!**

**Compartilhe com outros desenvolvedores!**

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---
*Desenvolvido por Isaac Nogueira*
