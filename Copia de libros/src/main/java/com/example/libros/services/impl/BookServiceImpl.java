package com.example.libros.services.impl;

import com.example.libros.dto.request.BookRequest;
import com.example.libros.dto.response.BookResponse;
import com.example.libros.entities.Book;
import com.example.libros.exceptions.BadRequestException;
import com.example.libros.exceptions.ResourceNotFoundException;
import com.example.libros.repositories.BookRepository;
import com.example.libros.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    public BookResponse create(BookRequest dto) {

        repository.findByIsbn(dto.getIsbn())
                .ifPresent(book -> {
                    throw new BadRequestException(
                            "ISBN already exists"
                    );
                });

        validateBook(dto);

        Book book = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .publicationYear(dto.getPublicationYear())
                .language(dto.getLanguage())
                .pages(dto.getPages())
                .build();

        repository.save(book);

        return mapToResponse(book);
    }

    @Override
    public List<BookResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BookResponse getById(Long id) {

        Book book = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));

        return mapToResponse(book);
    }

    @Override
    public BookResponse update(Long id, BookRequest dto) {

        Book book = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));

        repository.findByIsbn(dto.getIsbn())
                .ifPresent(existing -> {

                    if(!existing.getId().equals(id)) {

                        throw new BadRequestException(
                                "ISBN already exists"
                        );
                    }
                });

        validateBook(dto);

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        book.setLanguage(dto.getLanguage());
        book.setPages(dto.getPages());

        repository.save(book);

        return mapToResponse(book);
    }

    @Override
    public void delete(Long id) {

        Book book = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));

        repository.delete(book);
    }

    @Override
    public List<BookResponse> filterByAuthor(String author) {

        return repository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<BookResponse> filterByLanguage(String language) {

        return repository.findByLanguageIgnoreCase(language)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<BookResponse> filterByPages(
            Integer min,
            Integer max
    ) {

        return repository.findByPagesBetween(min, max)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void validateBook(BookRequest dto) {

        if(dto.getTitle().matches("\\d+")) {

            throw new BadRequestException(
                    "Title cannot contain only numbers"
            );
        }

        int currentYear = Year.now().getValue();

        if(dto.getPublicationYear() < 1900
                || dto.getPublicationYear() > currentYear) {

            throw new BadRequestException(
                    "Invalid publication year"
            );
        }
    }

    private BookResponse mapToResponse(Book book) {

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .language(book.getLanguage())
                .pages(book.getPages())
                .build();
    }
}