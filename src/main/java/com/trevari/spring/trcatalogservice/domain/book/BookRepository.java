package com.trevari.spring.trcatalogservice.domain.book;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Page<Book> findPage(Pageable pageable);
    Optional<Book> findByIsbn(String isbn);
}