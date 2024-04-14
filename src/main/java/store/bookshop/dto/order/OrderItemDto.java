package store.bookshop.dto.order;

public record OrderItemDto(Long id,
                           Long bookId,
                           int quantity) {
}
