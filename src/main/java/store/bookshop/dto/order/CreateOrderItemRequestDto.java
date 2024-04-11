package store.bookshop.dto.order;

import lombok.Data;

@Data
public class CreateOrderItemRequestDto {
    private Long bookId;
    private int quantity;
}
