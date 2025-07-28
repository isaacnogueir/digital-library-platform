-- Usuários Administradores
INSERT INTO users (login, password, nome, email, telefone, endereco, role, active, created_at, updated_at)
VALUES
('admin_master', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Administrador Master do Sistema', 'admin.master@biblioteca.com', '21987654321', 'Rua Principal, 123', 'ADMIN', true, NOW(), NOW()),
('admin_tech', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Administrador Técnico TI', 'admin.tech@biblioteca.com', '21987654322', 'Avenida Tecnologia, 456', 'ADMIN', true, NOW(), NOW()),
('admin_geral', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Administrador Geral Sistema', 'admin.geral@biblioteca.com', '21987654323', 'Rua Administração, 789', 'ADMIN', true, NOW(), NOW());

-- Bibliotecários
INSERT INTO users (login, password, nome, email, telefone, endereco, role, active, created_at, updated_at)
VALUES
('bibliotecario1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Maria Silva Santos', 'maria.silva@biblioteca.com', '21976543210', 'Avenida Central, 456', 'LIBRARIAN', true, NOW(), NOW()),
('bibliotecario2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'João Pedro Oliveira', 'joao.pedro@biblioteca.com', '21965432109', 'Rua das Flores, 789', 'LIBRARIAN', true, NOW(), NOW()),
('biblio_noite', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKcnw2wJM8uc/Lna5EMXcsWEu', 'Ana Carolina Ferreira', 'ana.ferreira@biblioteca.com', '21965432108', 'Rua do Livro, 456', 'LIBRARIAN', true, NOW(), NOW());

-- Usuários comuns
INSERT INTO users (login, password, nome, email, telefone, endereco, role, active, created_at, updated_at)
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
-- INSERÇÃO DE LIVROS
-- =========================================

-- Clássicos da Literatura
INSERT INTO books (title, author, isbn, publication_year, publisher_name, status, active, created_at, updated_at)
VALUES
('Dom Quixote', 'Miguel de Cervantes', '978-85-359-0277-5', 1605, 'Editora Moderna', 'AVAILABLE', true, NOW(), NOW()),
('1984', 'George Orwell', '978-85-250-4393-4', 1949, 'Companhia das Letras', 'AVAILABLE', true, NOW(), NOW()),
('O Pequeno Príncipe', 'Antoine de Saint-Exupéry', '978-85-359-0635-3', 1943, 'Editora Ática', 'LOANED', true, NOW(), NOW()),
('Orgulho e Preconceito', 'Jane Austen', '978-85-359-2847-8', 1813, 'Penguin Classics', 'AVAILABLE', true, NOW(), NOW()),
('Cem Anos de Solidão', 'Gabriel García Márquez', '978-85-359-0012-2', 1967, 'Record', 'AVAILABLE', true, NOW(), NOW());

-- Literatura Brasileira
INSERT INTO books (title, author, isbn, publication_year, publisher_name, status, active, created_at, updated_at)
VALUES
('O Cortiço', 'Aluísio Azevedo', '978-85-16-03847-2', 1890, 'Editora Scipione', 'AVAILABLE', true, NOW(), NOW()),
('Dom Casmurro', 'Machado de Assis', '978-85-359-0123-5', 1899, 'Editora Globo', 'LOANED', true, NOW(), NOW()),
('O Guarani', 'José de Alencar', '978-85-16-04521-0', 1857, 'Editora FTD', 'AVAILABLE', true, NOW(), NOW()),
('Quincas Borba', 'Machado de Assis', '978-85-359-0456-4', 1891, 'Editora Globo', 'AVAILABLE', true, NOW(), NOW()),
('Iracema', 'José de Alencar', '978-85-16-05234-8', 1865, 'Editora Ática', 'UNAVAILABLE', false, NOW(), NOW());

-- Ficção Científica e Fantasia
INSERT INTO books (title, author, isbn, publication_year, publisher_name, status, active, created_at, updated_at)
VALUES
('Duna', 'Frank Herbert', '978-85-7522-484-4', 1965, 'Aleph', 'AVAILABLE', true, NOW(), NOW()),
('Fundação', 'Isaac Asimov', '978-85-7522-125-6', 1951, 'Aleph', 'AVAILABLE', true, NOW(), NOW()),
('O Hobbit', 'J.R.R. Tolkien', '978-85-7522-789-0', 1937, 'HarperCollins', 'LOANED', true, NOW(), NOW()),
('Neuromancer', 'William Gibson', '978-85-7522-456-1', 1984, 'Aleph', 'AVAILABLE', true, NOW(), NOW()),
('O Guia do Mochileiro das Galáxias', 'Douglas Adams', '978-85-7522-123-2', 1979, 'Arqueiro', 'AVAILABLE', true, NOW(), NOW());

-- Tecnologia e Programação
INSERT INTO books (title, author, isbn, publication_year, publisher_name, status, active, created_at, updated_at)
VALUES
('Clean Code', 'Robert C. Martin', '978-85-7522-901-6', 2008, 'Alta Books', 'AVAILABLE', true, NOW(), NOW()),
('Java: Como Programar', 'Paul Deitel', '978-85-430-2348-9', 2017, 'Pearson', 'LOANED', true, NOW(), NOW()),
('Padrões de Projetos', 'Erich Gamma', '978-85-7522-567-4', 1994, 'Bookman', 'AVAILABLE', true, NOW(), NOW()),
('Spring Boot em Ação', 'Craig Walls', '978-85-7522-890-3', 2016, 'Novatec', 'AVAILABLE', true, NOW(), NOW()),
('Algoritmos: Teoria e Prática', 'Thomas H. Cormen', '978-85-352-3699-6', 2012, 'Elsevier', 'AVAILABLE', true, NOW(), NOW());

-- =========================================
-- INSERÇÃO DE EMPRÉSTIMOS
-- =========================================

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

-- =========================================
-- COMENTÁRIOS E INFORMAÇÕES ADICIONAIS
-- =========================================

-- Senha padrão para todos os usuários: "123456" (criptografada com BCrypt)
-- Status dos livros:
-- - AVAILABLE: Disponível para empréstimo
-- - LOANED: Atualmente emprestado
-- - UNAVAILABLE: Indisponível (ex: Iracema está inativo)

-- Usuários:
-- ID 1-3: admin_master, admin_tech, admin_geral (ADMIN)
-- ID 4-6: bibliotecario1, bibliotecario2, biblio_noite (LIBRARIAN)
-- ID 7-21: mariaclara, carlos123, ana_costa, pedrohenri, juliana_s, rodrigo_dev,
--          fernanda_ui, lucas_gamer, camila_prof, bruno_eng, larissa_med,
--          gabriel_art, patricia_adv, ricardo_chef, isabela_des (USER)

-- Livros com status LOANED:
-- ID 3: O Pequeno Príncipe (emprestado para Maria Clara - ID 7)
-- ID 7: Dom Casmurro (emprestado para Carlos - ID 8)
-- ID 13: O Hobbit (emprestado para Pedro - ID 10)
-- ID 17: Java: Como Programar (emprestado para Juliana - ID 11)
-- ID 16: Clean Code (emprestado para Rodrigo - ID 12)
-- ID 15: O Guia do Mochileiro (emprestado para Lucas - ID 14)