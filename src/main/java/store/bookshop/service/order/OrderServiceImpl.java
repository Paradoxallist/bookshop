package store.bookshop.service.order;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
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
import store.bookshop.model.Book;
import store.bookshop.model.Order;
import store.bookshop.model.OrderItem;
import store.bookshop.repository.BookRepository;
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
    private final BookRepository bookRepository;

    @Override
    public List<OrderDto> findAll(Long userId) {
        return orderMapper.toDtoList(orderRepository.findByUserId(userId));
    }

    @Override
    @Transactional
    public OrderDto createOrder(Long userId, CreateOrderRequestDto requestDto) {
        Order newOrder = new Order();

        List<OrderItem> orderItems = orderItemMapper.toModelList(requestDto.getOrderItems());
        BigDecimal total = new BigDecimal(0);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(newOrder);
            Book book = bookRepository.findById(orderItem.getBook().getId()).orElseThrow(
                    () -> new EntityNotFoundException("Can't find book by id: "
                            + orderItem.getBook().getId()));
            BigDecimal price = book.getPrice();
            BigDecimal bookPrices = price.multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            total = total.add(bookPrices);
            orderItem.setPrice(price);
        }

        newOrder.setTotal(total);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setUser(userRepository.getReferenceById(userId));
        newOrder.setStatus(Order.Status.COMPLETED);
        newOrder.setShippingAddress(requestDto.getShippingAddress());
        Order save = orderRepository.save(newOrder);

        for (OrderItem orderItem : orderItems) {
            orderItemRepository.save(orderItem);
        }

        Set<OrderItem> orderItemSet = new LinkedHashSet<>(orderItems);
        save.setOrderItems(orderItemSet);

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
        return orderItemMapper.toDtoList(
                orderItemRepository.findByUserIdAndOrderId(userId, orderId));
    }

    @Override
    public OrderItemDto getOrderItemByOrderIdAndItemId(
            Long userId,
            Long orderId,
            Long itemId
    ) {
        Optional<OrderItem> orderItem =
                orderItemRepository.findByUserIdAndOrderIdAndItemId(userId, orderId, itemId);
        if (orderItem.isEmpty()) {
            throw new EntityNotFoundException("Can't find OrderItem by id: " + itemId);
        }
        return orderItemMapper.toDto(orderItem.get());
    }
}
