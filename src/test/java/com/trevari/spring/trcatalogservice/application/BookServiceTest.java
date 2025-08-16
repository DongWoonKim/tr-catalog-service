package com.trevari.spring.trcatalogservice.application;

import com.trevari.spring.trcatalogservice.domain.book.Book;
import com.trevari.spring.trcatalogservice.domain.book.Isbn;
import com.trevari.spring.trcatalogservice.infrastructure.persistence.FakeBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {// Fake Repository 준비
        FakeBookRepository fakeRepo = new FakeBookRepository();
        fakeRepo.save(
                new Book(
                        new Isbn("9780132350884"),
                        "Effective Java",
                        "Best practices",
                        "Joshua Bloch",
                        "Addison-Wesley",
                        LocalDate.parse("2018-01-06"), "./")
        );
        fakeRepo.save(
                new Book(
                        new Isbn("9780134685991"),
                        "Clean Code",
                        "A Handbook of Agile Software Craftsmanship",
                        "Robert C. Martin",
                        "Prentice Hall",
                        LocalDate.parse("2008-08-01"), "./")
        );

        // BookService에 주입
        bookService = new BookService(fakeRepo);
    }

    @Test
    void 책정보_전체_조회_테스트() {
        // when
        var page = bookService.getBooks(0, 20);

        // then
        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("Effective Java");
        assertThat(page.getContent().get(1).getTitle()).isEqualTo("Clean Code");
    }

    @Test
    void 책정보가_없으면_빈리스트_반환_테스트() {
        // given
        FakeBookRepository emptyRepo = new FakeBookRepository();
        BookService emptyService = new BookService(emptyRepo);

        // when
        var page = emptyService.getBooks(0, 10); // ← 페이징 API 사용

        // then
        assertThat(page.getTotalElements()).isZero();
        assertThat(page.getContent()).isEmpty();
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
    }

}