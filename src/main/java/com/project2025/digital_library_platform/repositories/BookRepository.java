package com.project2025.digital_library_platform.repositories;


import com.project2025.digital_library_platform.entity.book.Book;
import com.project2025.digital_library_platform.entity.book.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitle(String title);

    List<Book> findByActiveAndStatus(Boolean active, Status status);

    List<Book> findByTitleContainingIgnoreCase(String title);

    boolean existsByIsbn10(String isbn10);

    boolean existsByIsbn13(String isbn13);

    boolean existsByGoogleBooksId(String gooleBooksId);

    @Query("SELECT b FROM Book b where b.isbn10 = ?1 OR b.isbn13 = ?2")
    Optional<Book> findByIsbn10OrIsbn13(String isbn10, String isbn13);

    // Optional<Book> findByPublisherNameContainingIgnoreCase(String publisherName);

    // List<Book> findByAuthorsContainingIgnoreCase(String authors);

    //  Optional<Book> findByIsbn(String isbn);

    //  List<Book> findByPublicationYear(Integer year);

    //  boolean existsByIsbn(String isbn);
}