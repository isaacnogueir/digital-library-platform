package com.project2025.digital_library_platform.repositories;


import com.project2025.digital_library_platform.domain.book.Book;
import com.project2025.digital_library_platform.domain.book.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    // ===== BUSCAS EXATAS =====

    /**
     * Verifica se existe um livro com o ISBN informado
     *
     * @param isbn código ISBN do livro
     */
    boolean existsByIsbn(String isbn);

    /**
     * Verifica se existe um livro com o título exato informado
     *
     * @param title título exato do livro
     */
    boolean existsByTitle(String title);

    /**
     * Busca um livro pelo código ISBN
     *
     * @param isbn código ISBN do livro para busca
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Busca livros pelo ano de publicação
     *
     * @param year ano de publicação do livro
     */
    List<Book> findByPublicationYear(Integer year);

    /**
     * Busca livros por status de ativo e status do livro
     *
     * @param active true para livros ativos, false para inativos
     * @param status status do livro (AVAILABLE, LOANED, UNAVAILABLE)
     */
    List<Book> findByActiveAndStatus(Boolean active, Status status);

    // ===== BUSCAS CASE INSENSITIVE E CONTAINING =====

    /**
     * Busca livros por autor - case insensitive e containing
     *
     * @param author nome ou parte do nome do autor
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);

    /**
     * Busca livros por título - case insensitive e containing
     *
     * @param title título ou parte do título do livro
     */
    Optional<Book> findByTitleContainingIgnoreCase(String title);

    /**
     * Busca livros por nome da editora - case insensitive e containing
     *
     * @param publisherName nome ou parte do nome da editora
     */
    Optional<Book> findByPublisherNameContainingIgnoreCase(String publisherName);
}