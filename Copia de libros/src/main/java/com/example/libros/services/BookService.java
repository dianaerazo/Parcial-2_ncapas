package com.example.libros.services;

import com.example.libros.dto.request.BookRequest;
import com.example.libros.dto.response.BookResponse;

import java.util.List;

public interface BookService {

    BookResponse create(BookRequest dto);

    List<BookResponse> getAll();

    BookResponse getById(Long id);

    BookResponse update(Long id, BookRequest dto);

    void delete(Long id);

    List<BookResponse> filterByAuthor(String author);

    List<BookResponse> filterByLanguage(String language);

    List<BookResponse> filterByPages(
            Integer min,
            Integer max
    );
}