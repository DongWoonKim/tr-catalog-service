package com.trevari.spring.trcatalogservice.domain.book;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class Book {
    private final Isbn isbn;
    private final String title;
    private final String subtitle;
    private final String author;
    private final String publisher;
    private final LocalDate publishedDate;
    private final String imageUrl;
}
