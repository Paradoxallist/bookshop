package store.bookshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;
    @NotNull
    @Column(name = "author", nullable = false)
    private String author;
    @NotNull
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;
    @NotNull
    @Min(value = 0)
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    private String description;
    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
