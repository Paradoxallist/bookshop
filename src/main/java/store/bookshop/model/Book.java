package store.bookshop.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nonnull
    private String title;
    @Nonnull
    private String author;
    @Nonnull
    @Column(unique = true)
    private String isbn;
    @Nonnull
    private BigDecimal price;
    private String description;
    private String coverImage;

    public Book(String title, String author, String isbn, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    public Book() {
    }
}
