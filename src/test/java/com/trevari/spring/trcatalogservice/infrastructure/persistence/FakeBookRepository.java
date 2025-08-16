package com.trevari.spring.trcatalogservice.infrastructure.persistence;

import com.trevari.spring.trcatalogservice.domain.book.Book;
import com.trevari.spring.trcatalogservice.domain.book.BookRepository;
import com.trevari.spring.trcatalogservice.domain.book.Isbn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

public class FakeBookRepository implements BookRepository {

    private final Map<String, Book> store = new LinkedHashMap<>();

    @Override
    public Book save(Book book) {
        store.put(book.getIsbn().normalized(), book);
        return book;
    }

    @Override
    public Page<Book> findPage(Pageable pageable) {
        // publishedDate desc nulls last 정렬
        Comparator<Book> cmp = Comparator
                .comparing(Book::getPublishedDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
                .reversed();

        List<Book> sorted = store.values().stream()
                .sorted(cmp)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sorted.size());
        List<Book> content = start >= end ? List.of() : sorted.subList(start, end);

        return new PageImpl<>(content, pageable, sorted.size());
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(store.get(new Isbn(isbn).normalized()));
    }

    public void clear() {
        store.clear();
    }
}
