package store.bookshop.dto.order;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class CreateOrderRequestDto {
    @NotNull
    private String shippingAddress;

    private List<CreateOrderItemRequestDto> orderItems;
}
