package com.example.libros.controller;

import com.example.libros.dto.request.BookRequest;
import com.example.libros.dto.response.BookResponse;
import com.example.libros.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping
    public BookResponse create(
            @Valid @RequestBody BookRequest dto
    ) {

        return service.create(dto);
    }

    @GetMapping
    public List<BookResponse> getAll() {

        return service.getAll();
    }

    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable Long id) {

        return service.getById(id);
    }

    @PutMapping("/{id}")
    public BookResponse update(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest dto
    ) {

        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        service.delete(id);
    }

    @GetMapping("/author")
    public List<BookResponse> filterByAuthor(
            @RequestParam String author
    ) {

        return service.filterByAuthor(author);
    }

    @GetMapping("/language")
    public List<BookResponse> filterByLanguage(
            @RequestParam String language
    ) {

        return service.filterByLanguage(language);
    }

    @GetMapping("/pages")
    public List<BookResponse> filterByPages(
            @RequestParam Integer min,
            @RequestParam Integer max
    ) {

        return service.filterByPages(min, max);
    }
}
