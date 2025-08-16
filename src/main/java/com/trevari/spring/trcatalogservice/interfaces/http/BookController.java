package com.trevari.spring.trcatalogservice.interfaces.http;

import com.trevari.spring.trcatalogservice.application.BookService;
import com.trevari.spring.trcatalogservice.domain.book.Book;
import com.trevari.spring.trcatalogservice.interfaces.dto.BookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping
    public Page<BookResponseDTO> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Page<Book> books = service.getBooks(page, size);

        return books.map(BookResponseDTO::from);
    }

    @GetMapping("/{isbn}")
    public BookResponseDTO getOne(@PathVariable String isbn) {
        return service.getBookByIsbn(isbn)
                .map(BookResponseDTO::from)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "book not found: " + isbn));
    }
}
