package store.bookshop.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import store.bookshop.model.Order;

public record OrderDto(Long id,
                       Long userId,
                       List<OrderItemDto> orderItems,
                       LocalDateTime orderDate,
                       BigDecimal total,
                       Order.Status status) {
}
