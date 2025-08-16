package com.trevari.spring.trcatalogservice.infrastructure.persistence;

import com.trevari.spring.trcatalogservice.domain.book.Book;
import com.trevari.spring.trcatalogservice.domain.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryAdapter implements BookRepository {
    private final BookJpaRepository jpa;
    private final BookMapper mapper;

    @Override
    public Book save(Book book) {
        BookEntity saved = jpa.save(mapper.toEntity(book));
        return mapper.toDomain(saved);
    }

    @Override
    public Page<Book> findPage(Pageable pageable) {
        Page<BookEntity> page = jpa.findPage(pageable);
        return page.map(mapper::toDomain);
    }


    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return jpa.findByIsbn(isbn)
                .map(mapper::toDomain);
    }
}
