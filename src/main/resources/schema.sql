-- Tabela de usuários
DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    endereco VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'LIBRARIAN', 'USER')),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de livros
CREATE TABLE books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL UNIQUE,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(100) NOT NULL UNIQUE,
    publication_year INTEGER NOT NULL,
    publisher_name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('AVAILABLE', 'LOANED', 'UNAVAILABLE')),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de empréstimos
CREATE TABLE loans (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    loan_date TIMESTAMP NOT NULL,
    return_date TIMESTAMP NOT NULL,
    returned BOOLEAN NOT NULL DEFAULT FALSE,
    actual_return_date TIMESTAMP NULL,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Foreign Keys
    CONSTRAINT fk_loan_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT,
    CONSTRAINT fk_loan_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE RESTRICT
);

-- =========================================
-- ÍNDICES PARA OTIMIZAÇÃO DE CONSULTAS
-- =========================================

-- Índices da tabela users
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_role ON users(role);
CREATE INDEX idx_user_active ON users(active);

-- Índices da tabela books
CREATE INDEX idx_book_title ON books(title);
CREATE INDEX idx_book_status ON books(status);
CREATE INDEX idx_book_active ON books(active);

-- Índices da tabela loans
CREATE INDEX idx_loan_user_id ON loans(user_id);
CREATE INDEX idx_loan_book_id ON loans(book_id);
CREATE INDEX idx_loan_returned ON loans(returned);

-- =========================================
-- COMENTÁRIOS NAS TABELAS E COLUNAS
-- =========================================

-- Comentários da tabela users
ALTER TABLE users COMMENT = 'Tabela de usuários do sistema de biblioteca';
ALTER TABLE users MODIFY COLUMN id BIGINT AUTO_INCREMENT COMMENT 'Identificador único do usuário';
ALTER TABLE users MODIFY COLUMN login VARCHAR(20) NOT NULL COMMENT 'Login único do usuário (3-20 caracteres)';
ALTER TABLE users MODIFY COLUMN password VARCHAR(100) NOT NULL COMMENT 'Senha criptografada do usuário';
ALTER TABLE users MODIFY COLUMN nome VARCHAR(100) NOT NULL COMMENT 'Nome completo do usuário';
ALTER TABLE users MODIFY COLUMN email VARCHAR(100) NOT NULL COMMENT 'Email único do usuário';
ALTER TABLE users MODIFY COLUMN telefone VARCHAR(20) NOT NULL COMMENT 'Telefone do usuário';
ALTER TABLE users MODIFY COLUMN endereco VARCHAR(50) NOT NULL COMMENT 'Endereço do usuário';
ALTER TABLE users MODIFY COLUMN role VARCHAR(20) NOT NULL COMMENT 'Papel do usuário no sistema (ADMIN, LIBRARIAN, USER)';
ALTER TABLE users MODIFY COLUMN active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'Indica se o usuário está ativo';

-- Comentários da tabela books
ALTER TABLE books COMMENT = 'Tabela de livros disponíveis na biblioteca';
ALTER TABLE books MODIFY COLUMN id BIGINT AUTO_INCREMENT COMMENT 'Identificador único do livro';
ALTER TABLE books MODIFY COLUMN title VARCHAR(100) NOT NULL COMMENT 'Título único do livro';
ALTER TABLE books MODIFY COLUMN author VARCHAR(100) NOT NULL COMMENT 'Nome do autor do livro';
ALTER TABLE books MODIFY COLUMN isbn VARCHAR(100) NOT NULL COMMENT 'ISBN único do livro';
ALTER TABLE books MODIFY COLUMN publication_year INTEGER NOT NULL COMMENT 'Ano de publicação do livro';
ALTER TABLE books MODIFY COLUMN publisher_name VARCHAR(100) NOT NULL COMMENT 'Nome da editora';
ALTER TABLE books MODIFY COLUMN status VARCHAR(20) NOT NULL COMMENT 'Status do livro (AVAILABLE, LOANED, UNAVAILABLE)';
ALTER TABLE books MODIFY COLUMN active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'Indica se o livro está ativo no sistema';

-- Comentários da tabela loans
ALTER TABLE loans COMMENT = 'Tabela de empréstimos de livros';
ALTER TABLE loans MODIFY COLUMN id BIGINT AUTO_INCREMENT COMMENT 'Identificador único do empréstimo';
ALTER TABLE loans MODIFY COLUMN loan_date TIMESTAMP NOT NULL COMMENT 'Data e hora do empréstimo';
ALTER TABLE loans MODIFY COLUMN return_date TIMESTAMP NOT NULL COMMENT 'Data prevista para devolução';
ALTER TABLE loans MODIFY COLUMN returned BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Indica se o livro foi devolvido';
ALTER TABLE loans MODIFY COLUMN actual_return_date TIMESTAMP NULL COMMENT 'Data real da devolução';
ALTER TABLE loans MODIFY COLUMN user_id BIGINT NOT NULL COMMENT 'ID do usuário que fez o empréstimo';
ALTER TABLE loans MODIFY COLUMN book_id BIGINT NOT NULL COMMENT 'ID do livro emprestado';