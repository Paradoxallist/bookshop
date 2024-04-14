package store.bookshop.service.order;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
import store.bookshop.model.CartItem;
import store.bookshop.model.Order;
import store.bookshop.model.OrderItem;
import store.bookshop.model.ShoppingCart;
import store.bookshop.repository.BookRepository;
import store.bookshop.repository.CartItemRepository;
import store.bookshop.repository.OrderItemRepository;
import store.bookshop.repository.OrderRepository;
import store.bookshop.repository.ShoppingCartRepository;
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
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public List<OrderDto> findAll(Long userId) {
        return orderMapper.toDtoList(orderRepository.findByUserId(userId));
    }

    @Override
    @Transactional
    public OrderDto createOrder(Long userId, CreateOrderRequestDto requestDto) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        validateCartItems(shoppingCart);

        Order newOrder = buildOrder(userId, requestDto);

        List<OrderItem> orderItems = createOrderItems(shoppingCart, newOrder);
        BigDecimal total = calculateTotal(orderItems);
        newOrder.setTotal(total);
        newOrder.setOrderItems(new LinkedHashSet<>(orderItems));

        Order savedOrder = orderRepository.save(newOrder);

        return orderMapper.toDto(savedOrder);
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
        OrderItem orderItem =
                orderItemRepository.findByUserIdAndOrderIdAndItemId(userId, orderId, itemId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Can't find OrderItem by id: " + itemId));
        return orderItemMapper.toDto(orderItem);
    }

    private ShoppingCart getShoppingCart(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    private void validateCartItems(ShoppingCart shoppingCart) {
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new EntityNotFoundException("No CartItems to create order");
        }
    }

    private Order buildOrder(Long userId, CreateOrderRequestDto requestDto) {
        Order newOrder = new Order();
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setUser(userRepository.getReferenceById(userId));
        newOrder.setStatus(Order.Status.COMPLETED);
        newOrder.setShippingAddress(requestDto.getShippingAddress());
        return newOrder;
    }

    private List<OrderItem> createOrderItems(ShoppingCart shoppingCart, Order newOrder) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderItem orderItem = createOrderItem(cartItem, newOrder);
            orderItems.add(orderItem);
            cartItemRepository.deleteById(cartItem.getId());
        }
        return orderItems;
    }

    private OrderItem createOrderItem(CartItem cartItem, Order newOrder) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(newOrder);
        Book book = getBook(cartItem.getBook().getId());
        BigDecimal price = book.getPrice();
        int quantity = cartItem.getQuantity();
        orderItem.setPrice(price);
        orderItem.setBook(book);
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + bookId));
    }

    private BigDecimal calculateTotal(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem ->
                        orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
