package store.bookshop.service.order;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.bookshop.dto.order.CreateOrderRequestDto;
import store.bookshop.dto.order.OrderDto;
import store.bookshop.dto.order.OrderItemDto;
import store.bookshop.dto.order.UpdateOrderRequestDto;
import store.bookshop.exception.EntityNotFoundException;
import store.bookshop.mapper.OrderItemMapper;
import store.bookshop.mapper.OrderMapper;
import store.bookshop.model.Order;
import store.bookshop.model.OrderItem;
import store.bookshop.repository.OrderItemRepository;
import store.bookshop.repository.OrderRepository;
import store.bookshop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderDto> findAll(Long userId) {
        return orderMapper.toDtoList(orderRepository.findByUserId(userId));
    }

    @Override
    @Transactional
    public OrderDto createOrder(Long userId, CreateOrderRequestDto requestDto) {
        Order newOrder = new Order();
        newOrder.setTotal(BigDecimal.valueOf(0));
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setUser(userRepository.getReferenceById(userId));
        newOrder.setStatus(Order.Status.COMPLETED);
        newOrder.setShippingAddress(requestDto.getShippingAddress());
        Order save = orderRepository.save(newOrder);
        return orderMapper.toDto(save);
    }

    @Override
    @Transactional
    public OrderDto update(Long orderId, UpdateOrderRequestDto requestDto) {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Can't find Order by id: " + orderId);
        }
        Order order = orderRepository.getReferenceById(orderId);
        order.setStatus(requestDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long userId, Long orderId) {
        userComplianceCheck(userId, orderId);
        return orderItemMapper.toDtoList(orderItemRepository.findByOrderId(orderId));
    }

    @Override
    public OrderItemDto getOrderItemByOrderIdAndItemId(Long userId, Long orderId, Long itemId) {
        userComplianceCheck(userId, orderId);
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
        Optional<OrderItem> orderItem = orderItemList.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst();
        if (orderItem.isEmpty()) {
            throw new EntityNotFoundException("Can't find OrderItem by id: " + itemId);
        }
        return orderItemMapper.toDto(orderItem.get());
    }

    private void userComplianceCheck(Long userId, Long orderId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        if (orderList.stream()
                .noneMatch(order -> order.getId().equals(orderId))) {
            throw new SecurityException("This user does not have access to this order");
        }
    }
}
