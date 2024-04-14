package store.bookshop.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import store.bookshop.model.Order;

@Data
public class UpdateOrderRequestDto {
    @NotNull
    private Order.Status status;
}
