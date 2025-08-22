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

CREATE TABLE books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    google_books_id VARCHAR(255) UNIQUE,
    title VARCHAR(255) NOT NULL,
    authors TEXT,
    publisher VARCHAR(255),
    published_date VARCHAR(50),
    isbn_10 VARCHAR(20),
    isbn_13 VARCHAR(20),
    description TEXT,
    thumbnail_url VARCHAR(500),
    page_count INTEGER,

    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE' CHECK (status IN ('AVAILABLE', 'LOANED', 'UNAVAILABLE')),
    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

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

    CONSTRAINT fk_loan_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT,
    CONSTRAINT fk_loan_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE RESTRICT
);

CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_role ON users(role);
CREATE INDEX idx_user_active ON users(active);

CREATE INDEX idx_book_title ON books(title);
CREATE INDEX idx_book_status ON books(status);
CREATE INDEX idx_book_active ON books(active);
CREATE INDEX idx_book_google_id ON books(google_books_id);

CREATE INDEX idx_loan_user_id ON loans(user_id);
CREATE INDEX idx_loan_book_id ON loans(book_id);
CREATE INDEX idx_loan_returned ON loans(returned);