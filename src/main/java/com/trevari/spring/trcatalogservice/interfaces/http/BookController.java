package com.trevari.spring.trcatalogservice.interfaces.http;

import com.trevari.spring.trcatalogservice.application.BookService;
import com.trevari.spring.trcatalogservice.domain.book.Book;
import com.trevari.spring.trcatalogservice.exception.BookNotFoundException;
import com.trevari.spring.trcatalogservice.interfaces.dto.BookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Books", description = "도서 조회 API")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @Operation(
            summary = "도서 목록 조회",
            description = "발행일 최신순 기준 페이징 조회"
    )
    @GetMapping
    public Page<BookResponseDTO> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return service.getBooks(page, size)
                .map(BookResponseDTO::from);
    }

    @Deprecated
    @Operation(
            summary = "도서 단건 조회",
            description = "ISBN(13자리)으로 도서 상세를 조회합니다",
            deprecated = true
    )
    @GetMapping("/{isbn}")
    public BookResponseDTO getOne(@PathVariable String isbn) {
        return service.getBookByIsbn(isbn)
                .map(BookResponseDTO::from)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }
}
