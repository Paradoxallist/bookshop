package store.bookshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(name = "title", nullable = false)
    private String title;
    @NonNull
    @Column(name = "author", nullable = false)
    private String author;
    @NonNull
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;
    @NonNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
