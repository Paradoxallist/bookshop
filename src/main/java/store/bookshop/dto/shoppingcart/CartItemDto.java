package store.bookshop.dto.shoppingcart;

public record CartItemDto(Long id, Long bookId, String bookTitle, int quantity) {
}
