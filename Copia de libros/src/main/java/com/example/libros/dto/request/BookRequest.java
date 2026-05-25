package com.example.libros.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Publication year is required")
    private Integer publicationYear;

    @NotBlank(message = "Language is required")
    private String language;

    @NotNull(message = "Pages are required")
    @Min(value = 11, message = "Pages must be greater than 10")
    private Integer pages;
}