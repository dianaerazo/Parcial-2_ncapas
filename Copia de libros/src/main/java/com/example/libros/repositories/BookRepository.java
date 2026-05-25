package com.example.libros.repositories;

import com.example.libros.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByLanguageIgnoreCase(String language);

    List<Book> findByPagesBetween(Integer min, Integer max);
}