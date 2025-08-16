package com.trevari.spring.trcatalogservice.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {

    @Query("select b from BookEntity b order by b.publishedDate desc nulls last")
    Page<BookEntity> findPage(Pageable pageable);

    Optional<BookEntity> findByIsbn(String isbn);
}
