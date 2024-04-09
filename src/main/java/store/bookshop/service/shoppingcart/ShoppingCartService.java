package store.bookshop.service.shoppingcart;

import store.bookshop.dto.shoppingcart.CartItemDto;
import store.bookshop.dto.shoppingcart.CreateCartItemRequestDto;
import store.bookshop.dto.shoppingcart.ShoppingCartDto;
import store.bookshop.dto.shoppingcart.UpdateCartItemRequestDto;
import store.bookshop.model.User;

public interface ShoppingCartService {

    void createShoppingCart(User user);

    ShoppingCartDto findByUserId(Long userId);

    CartItemDto save(Long userId, CreateCartItemRequestDto requestDto);

    CartItemDto updateById(Long userId, Long id, UpdateCartItemRequestDto requestDto);

    void deleteById(Long userId, Long cartItemId);
}
