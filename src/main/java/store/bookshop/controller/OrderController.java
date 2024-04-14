package store.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import store.bookshop.dto.order.CreateOrderRequestDto;
import store.bookshop.dto.order.OrderDto;
import store.bookshop.dto.order.OrderItemDto;
import store.bookshop.dto.order.UpdateOrderRequestDto;
import store.bookshop.model.User;
import store.bookshop.service.order.OrderService;

@Tag(name = "Order manager", description = "Endpoint for managing order")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get orders user")
    public List<OrderDto> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return orderService.findAll(user.getId());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new order")
    public OrderDto createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return orderService.createOrder(user.getId(), requestDto);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get order items by id")
    public List<OrderItemDto> getListOrderItems(@PathVariable("orderId") Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItems(user.getId(), orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get order item by id")
    public OrderItemDto getOrderItem(@PathVariable("orderId") Long orderId,
                                     @PathVariable("itemId") Long itemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItemByOrderIdAndItemId(user.getId(), orderId, itemId);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update order")
    public OrderDto updateOrderById(@RequestBody UpdateOrderRequestDto requestDto,
                                    @PathVariable("id") Long orderId) {
        return orderService.update(orderId, requestDto);
    }
}
