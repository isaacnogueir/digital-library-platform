-- Usuários Administradores
INSERT IGNORE INTO users (login, password, nome, email, telefone, endereco, role, active, created_at, updated_at)
VALUES
('admin_master', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Administrador Master do Sistema', 'admin.master@biblioteca.com', '21987654321', 'Rua Principal, 123', 'ADMIN', true, NOW(), NOW()),
('admin_tech', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Administrador Técnico TI', 'admin.tech@biblioteca.com', '21987654322', 'Avenida Tecnologia, 456', 'ADMIN', true, NOW(), NOW()),
('admin_geral', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Administrador Geral Sistema', 'admin.geral@biblioteca.com', '21987654323', 'Rua Administração, 789', 'ADMIN', true, NOW(), NOW());

-- Bibliotecários
INSERT IGNORE INTO users (login, password, nome, email, telefone, endereco, role, active, created_at, updated_at)
VALUES
('bibliotecario1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Maria Silva Santos', 'maria.silva@biblioteca.com', '21976543210', 'Avenida Central, 456', 'LIBRARIAN', true, NOW(), NOW()),
('bibliotecario2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'João Pedro Oliveira', 'joao.pedro@biblioteca.com', '21965432109', 'Rua das Flores, 789', 'LIBRARIAN', true, NOW(), NOW()),
('biblio_noite', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Ana Carolina Ferreira', 'ana.ferreira@biblioteca.com', '21965432108', 'Rua do Livro, 456', 'LIBRARIAN', true, NOW(), NOW());

-- Usuários comuns
INSERT IGNORE INTO users (login, password, nome, email, telefone, endereco, role, active, created_at, updated_at)
VALUES
('mariaclara', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Maria Clara Diniz', 'mariaclara@hotmail.com', '21955249865', 'Leblon, N 78', 'USER', true, NOW(), NOW()),
('carlos123', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Carlos Eduardo Silva', 'carlos.eduardo@gmail.com', '21944332211', 'Copacabana, 234', 'USER', true, NOW(), NOW()),
('ana_costa', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Ana Paula Costa', 'ana.costa@yahoo.com', '21933221100', 'Ipanema, 567', 'USER', true, NOW(), NOW()),
('pedrohenri', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Pedro Henrique Lima', 'pedro.lima@outlook.com', '21922110099', 'Botafogo, 890', 'USER', true, NOW(), NOW()),
('juliana_s', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Juliana Santos Rodrigues', 'juliana.santos@gmail.com', '21911009988', 'Flamengo, 123', 'USER', true, NOW(), NOW()),
('rodrigo_dev', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Rodrigo Almeida Santos', 'rodrigo.dev@gmail.com', '21900998877', 'Tijuca, 345', 'USER', true, NOW(), NOW()),
('fernanda_ui', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Fernanda Silva Oliveira', 'fernanda.ui@hotmail.com', '21899887766', 'Barra da Tijuca, 678', 'USER', true, NOW(), NOW()),
('lucas_gamer', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Lucas Fernando Souza', 'lucas.gamer@yahoo.com', '21888776655', 'Recreio, 901', 'USER', true, NOW(), NOW()),
('camila_prof', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Camila Rodrigues Lima', 'camila.prof@gmail.com', '21877665544', 'Madureira, 234', 'USER', true, NOW(), NOW()),
('bruno_eng', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Bruno Silva Engenheiro', 'bruno.eng@outlook.com', '21866554433', 'Campo Grande, 567', 'USER', true, NOW(), NOW()),
('larissa_med', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Larissa Santos Medeiros', 'larissa.med@hotmail.com', '21855443322', 'Jacarepaguá, 890', 'USER', true, NOW(), NOW()),
('gabriel_art', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Gabriel Oliveira Artista', 'gabriel.art@gmail.com', '21844332211', 'Santa Teresa, 123', 'USER', true, NOW(), NOW()),
('patricia_adv', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Patricia Lima Advogada', 'patricia.adv@yahoo.com', '21833221100', 'Centro, 456', 'USER', true, NOW(), NOW()),
('ricardo_chef', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Ricardo Santos Chef', 'ricardo.chef@gmail.com', '21822110099', 'Urca, 789', 'USER', true, NOW(), NOW()),
('isabela_des', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Isabela Costa Designer', 'isabela.des@hotmail.com', '21811009988', 'Lagoa, 012', 'USER', true, NOW(), NOW());

-- =========================================
-- INSERÇÃO DE LIVROS (usando INSERT IGNORE para evitar duplicatas)
-- =========================================

-- Clássicos da Literatura
INSERT IGNORE INTO books (google_books_id, title, authors, publisher, published_date, isbn_10, isbn_13, description, thumbnail_url, page_count, status, active, created_at, updated_at)
VALUES
(NULL, 'Dom Quixote', 'Miguel de Cervantes', 'Editora Moderna', '1605', '8535902775', '978-85-359-0277-5', 'A obra-prima da literatura espanhola que narra as aventuras de um fidalgo que decide se tornar cavaleiro andante.', NULL, 863, 'AVAILABLE', true, NOW(), NOW()),
(NULL, '1984', 'George Orwell', 'Companhia das Letras', '1949', '8525043934', '978-85-250-4393-4', 'Romance distópico que retrata uma sociedade totalitária onde o governo controla todos os aspectos da vida.', NULL, 416, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'O Pequeno Príncipe', 'Antoine de Saint-Exupéry', 'Editora Ática', '1943', '8535906353', '978-85-359-0635-3', 'Fábula poética sobre um piloto perdido no deserto que encontra um jovem príncipe de outro planeta.', NULL, 96, 'LOANED', true, NOW(), NOW()),
(NULL, 'Orgulho e Preconceito', 'Jane Austen', 'Penguin Classics', '1813', '8535928478', '978-85-359-2847-8', 'Romance clássico que explora temas de amor, reputação e classe na Inglaterra georgiana.', NULL, 432, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'Cem Anos de Solidão', 'Gabriel García Márquez', 'Record', '1967', '8535900122', '978-85-359-0012-2', 'Saga familiar que narra a história de sete gerações da família Buendía na cidade fictícia de Macondo.', NULL, 432, 'AVAILABLE', true, NOW(), NOW());

-- Literatura Brasileira
INSERT IGNORE INTO books (google_books_id, title, authors, publisher, published_date, isbn_10, isbn_13, description, thumbnail_url, page_count, status, active, created_at, updated_at)
VALUES
(NULL, 'O Cortiço', 'Aluísio Azevedo', 'Editora Scipione', '1890', '8516038472', '978-85-16-03847-2', 'Romance naturalista que retrata a vida em um cortiço no Rio de Janeiro do século XIX.', NULL, 304, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'Dom Casmurro', 'Machado de Assis', 'Editora Globo', '1899', '8535901235', '978-85-359-0123-5', 'Romance que narra a história de Bentinho e sua suspeita sobre a traição de Capitu.', NULL, 256, 'LOANED', true, NOW(), NOW()),
(NULL, 'O Guarani', 'José de Alencar', 'Editora FTD', '1857', '8516045210', '978-85-16-04521-0', 'Romance indianista que narra a história de amor entre Peri e Ceci.', NULL, 368, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'Quincas Borba', 'Machado de Assis', 'Editora Globo', '1891', '8535904564', '978-85-359-0456-4', 'Romance que continua a filosofia do Humanitismo iniciada em Memórias Póstumas de Brás Cubas.', NULL, 288, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'Iracema', 'José de Alencar', 'Editora Ática', '1865', '8516052348', '978-85-16-05234-8', 'Lenda do Ceará que narra a história da índia Iracema e do português Martim.', NULL, 144, 'UNAVAILABLE', false, NOW(), NOW());

-- Ficção Científica e Fantasia
INSERT IGNORE INTO books (google_books_id, title, authors, publisher, published_date, isbn_10, isbn_13, description, thumbnail_url, page_count, status, active, created_at, updated_at)
VALUES
(NULL, 'Duna', 'Frank Herbert', 'Aleph', '1965', '8575224844', '978-85-7522-484-4', 'Épico de ficção científica ambientado no planeta desértico Arrakis, fonte da especiaria melange.', NULL, 688, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'Fundação', 'Isaac Asimov', 'Aleph', '1951', '8575221256', '978-85-7522-125-6', 'Primeiro livro da saga que narra o declínio do Império Galáctico e a criação da Fundação.', NULL, 256, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'O Hobbit', 'J.R.R. Tolkien', 'HarperCollins', '1937', '8575227890', '978-85-7522-789-0', 'As aventuras de Bilbo Bolseiro em uma jornada épica com anões para recuperar um tesouro guardado por um dragão.', NULL, 336, 'LOANED', true, NOW(), NOW()),
(NULL, 'Neuromancer', 'William Gibson', 'Aleph', '1984', '8575224561', '978-85-7522-456-1', 'Romance cyberpunk que definiu o gênero, seguindo o hacker Case em um mundo de inteligência artificial.', NULL, 304, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'O Guia do Mochileiro das Galáxias', 'Douglas Adams', 'Arqueiro', '1979', '8575221232', '978-85-7522-123-2', 'Comédia de ficção científica sobre as aventuras de Arthur Dent pelo universo.', NULL, 224, 'AVAILABLE', true, NOW(), NOW());

-- Tecnologia e Programação
INSERT IGNORE INTO books (google_books_id, title, authors, publisher, published_date, isbn_10, isbn_13, description, thumbnail_url, page_count, status, active, created_at, updated_at)
VALUES
(NULL, 'Clean Code', 'Robert C. Martin', 'Alta Books', '2008', '8575229016', '978-85-7522-901-6', 'Manual sobre como escrever código limpo, legível e sustentável para desenvolvedores.', NULL, 464, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'Java: Como Programar', 'Paul Deitel, Harvey Deitel', 'Pearson', '2017', '8543023489', '978-85-430-2348-9', 'Livro abrangente sobre programação Java, cobrindo desde conceitos básicos até tópicos avançados.', NULL, 1152, 'LOANED', true, NOW(), NOW()),
(NULL, 'Padrões de Projetos', 'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides', 'Bookman', '1994', '8575225674', '978-85-7522-567-4', 'Catálogo de 23 padrões de design essenciais para desenvolvimento de software orientado a objetos.', NULL, 395, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'Spring Boot em Ação', 'Craig Walls', 'Novatec', '2016', '8575228903', '978-85-7522-890-3', 'Guia prático para desenvolvimento de aplicações Java com Spring Boot framework.', NULL, 320, 'AVAILABLE', true, NOW(), NOW()),
(NULL, 'Algoritmos: Teoria e Prática', 'Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein', 'Elsevier', '2012', '8535236996', '978-85-352-3699-6', 'Livro de referência sobre algoritmos, estruturas de dados e análise de complexidade.', NULL, 944, 'AVAILABLE', true, NOW(), NOW());

-- Empréstimos ativos (não devolvidos)
INSERT INTO loans (loan_date, return_date, returned, actual_return_date, user_id, book_id, created_at, updated_at)
VALUES
-- Maria Clara pegou O Pequeno Príncipe
(DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_ADD(NOW(), INTERVAL 9 DAY), false, NULL, 7, 3, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),

-- Carlos pegou Dom Casmurro
(DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 11 DAY), false, NULL, 8, 7, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),

-- Pedro pegou O Hobbit
(DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_ADD(NOW(), INTERVAL 7 DAY), false, NULL, 10, 13, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),

-- Juliana pegou Java: Como Programar
(DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 12 DAY), false, NULL, 11, 17, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),

-- Rodrigo pegou Clean Code
(DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 13 DAY), false, NULL, 12, 16, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),

-- Lucas pegou O Guia do Mochileiro
(DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_ADD(NOW(), INTERVAL 10 DAY), false, NULL, 14, 15, DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY));

-- Empréstimos já devolvidos (histórico)
INSERT INTO loans (loan_date, return_date, returned, actual_return_date, user_id, book_id, created_at, updated_at)
VALUES
-- Ana devolveu 1984
(DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY), true, DATE_SUB(NOW(), INTERVAL 8 DAY), 9, 2, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),

-- Fernanda devolveu Duna
(DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), true, DATE_SUB(NOW(), INTERVAL 3 DAY), 13, 11, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),

-- Camila devolveu Fundação
(DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY), true, DATE_SUB(NOW(), INTERVAL 12 DAY), 15, 12, DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY)),

-- Bruno devolveu O Cortiço
(DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 16 DAY), true, DATE_SUB(NOW(), INTERVAL 14 DAY), 16, 6, DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY)),

-- Larissa devolveu Neuromancer
(DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY), true, DATE_SUB(NOW(), INTERVAL 20 DAY), 17, 14, DATE_SUB(NOW(), INTERVAL 35 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),

-- Gabriel devolveu Padrões de Projetos
(DATE_SUB(NOW(), INTERVAL 40 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY), true, DATE_SUB(NOW(), INTERVAL 24 DAY), 18, 18, DATE_SUB(NOW(), INTERVAL 40 DAY), DATE_SUB(NOW(), INTERVAL 24 DAY)),

-- Patricia devolveu Orgulho e Preconceito
(DATE_SUB(NOW(), INTERVAL 45 DAY), DATE_SUB(NOW(), INTERVAL 31 DAY), true, DATE_SUB(NOW(), INTERVAL 28 DAY), 19, 4, DATE_SUB(NOW(), INTERVAL 45 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY));
