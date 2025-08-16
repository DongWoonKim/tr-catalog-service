package com.trevari.spring.trcatalogservice.domain.book;

public record Isbn(String value) {
    public Isbn {
        if (value == null) throw new IllegalArgumentException("ISBN required");
        String n = value.replaceAll("[-\\s]", "");
        if (!n.matches("\\d{13}")) throw new IllegalArgumentException("ISBN-13 only");
    }
    public String normalized() { return value.replaceAll("[-\\s]", ""); }
}
