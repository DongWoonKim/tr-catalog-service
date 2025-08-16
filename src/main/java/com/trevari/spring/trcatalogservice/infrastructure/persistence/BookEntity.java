package com.trevari.spring.trcatalogservice.infrastructure.persistence;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "book")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=20)
    private String isbn;

    @Column(nullable=false)
    private String title;

    private String subtitle;
    private String author;

    private String publisher;
    private LocalDate publishedDate;

    @Column(columnDefinition = "text")
    private String imageUrl;
}
