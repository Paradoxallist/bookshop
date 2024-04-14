package store.bookshop.service.order;

import java.util.List;
import store.bookshop.dto.order.CreateOrderRequestDto;
import store.bookshop.dto.order.OrderDto;
import store.bookshop.dto.order.OrderItemDto;
import store.bookshop.dto.order.UpdateOrderRequestDto;

public interface OrderService {
    List<OrderDto> findAll(Long userId);

    OrderDto createOrder(Long userId, CreateOrderRequestDto requestDto);

    OrderDto update(Long orderId, UpdateOrderRequestDto requestDto);

    List<OrderItemDto> getOrderItems(Long userId, Long orderId);

    OrderItemDto getOrderItemByOrderIdAndItemId(Long userId, Long orderId, Long itemId);
}
