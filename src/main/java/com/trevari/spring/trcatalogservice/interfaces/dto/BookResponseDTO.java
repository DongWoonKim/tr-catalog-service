package com.trevari.spring.trcatalogservice.interfaces.dto;

import com.trevari.spring.trcatalogservice.domain.book.Book;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BookResponseDTO {
    private final String isbn;
    private final String title;
    private final String subtitle;
    private final String author;
    private final String publisher;
    private final LocalDate publishedDate;

    public static BookResponseDTO from(Book book) {
        return BookResponseDTO.builder()
                .isbn( book.getIsbn().normalized() )
                .title( book.getTitle() )
                .subtitle( book.getSubtitle() )
                .author( book.getAuthor() )
                .publisher( book.getPublisher() )
                .publishedDate( book.getPublishedDate() )
                .build();
    }
}
