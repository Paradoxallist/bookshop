package store.bookshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.ISBN;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE books SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction(value = "is_deleted = FALSE")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "title", nullable = false)
    private String title;

    @NotEmpty
    @Column(name = "author", nullable = false)
    private String author;

    @NotEmpty
    @ISBN
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @NotNull
    @Min(value = 0)
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Size(min = 2, max = 200)
    private String description;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
