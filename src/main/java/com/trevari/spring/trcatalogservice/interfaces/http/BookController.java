package com.trevari.spring.trcatalogservice.interfaces.http;

import com.trevari.spring.trcatalogservice.application.BookService;
import com.trevari.spring.trcatalogservice.domain.book.Book;
import com.trevari.spring.trcatalogservice.interfaces.dto.BookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping
    public Page<BookResponseDTO> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Page<Book> books = service.getBooks(page, size);

        return books.map(BookResponseDTO::from);
    }
}
