package store.bookshop.dto.shoppingcart;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    private Long bookId;
    @Min(1)
    private int quantity;
}
