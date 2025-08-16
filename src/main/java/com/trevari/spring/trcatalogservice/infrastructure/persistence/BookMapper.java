package com.trevari.spring.trcatalogservice.infrastructure.persistence;

import com.trevari.spring.trcatalogservice.domain.book.Book;
import com.trevari.spring.trcatalogservice.domain.book.Isbn;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookEntity toEntity(Book b) {
        return BookEntity.builder()
                .isbn( b.getIsbn().normalized() )
                .title( b.getTitle() )
                .subtitle( b.getSubtitle() )
                .author( b.getAuthor() )
                .publisher( b.getPublisher() )
                .publishedDate( b.getPublishedDate() )
                .imageUrl( b.getImageUrl() )
                .build();
    }

    public Book toDomain(BookEntity e) {
        return Book.builder()
                .isbn( new Isbn(e.getIsbn()) )
                .title( e.getTitle() )
                .subtitle( e.getSubtitle() )
                .author( e.getAuthor() )
                .publisher( e.getPublisher() )
                .publishedDate( e.getPublishedDate() )
                .imageUrl( e.getImageUrl() )
                .build();
    }
}
