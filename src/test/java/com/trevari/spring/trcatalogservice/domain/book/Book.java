package com.trevari.spring.trcatalogservice.domain.book;

import java.time.LocalDate;

public class Book {
    private final Isbn isbn;
    private final String title;
    private final String subtitle;
    private final String author;
    private final String publisher;
    private final LocalDate publishedDate;
    private final String imageUrl;

    public Book(Isbn isbn, String title, String subtitle, String author, String publisher, LocalDate publishedDate, String imageUrl) {
        this.isbn = isbn;
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.imageUrl = imageUrl;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
