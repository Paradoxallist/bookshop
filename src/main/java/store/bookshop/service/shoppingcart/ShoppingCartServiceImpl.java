package store.bookshop.service.shoppingcart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.bookshop.dto.shoppingcart.CartItemDto;
import store.bookshop.dto.shoppingcart.CreateCartItemRequestDto;
import store.bookshop.dto.shoppingcart.ShoppingCartDto;
import store.bookshop.dto.shoppingcart.UpdateCartItemRequestDto;
import store.bookshop.exception.DuplicateCartItemException;
import store.bookshop.exception.EntityNotFoundException;
import store.bookshop.mapper.CartItemMapper;
import store.bookshop.mapper.ShoppingCartMapper;
import store.bookshop.model.CartItem;
import store.bookshop.model.ShoppingCart;
import store.bookshop.model.User;
import store.bookshop.repository.BookRepository;
import store.bookshop.repository.CartItemRepository;
import store.bookshop.repository.ShoppingCartRepository;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto findByUserId(Long userId) {
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUserId(userId));
    }

    @Override
    @Transactional
    public CartItemDto save(Long userId, CreateCartItemRequestDto requestDto) {
        if (!bookRepository.existsById(requestDto.getBookId())) {
            throw new EntityNotFoundException("Book doesn't exist with id: "
                    + requestDto.getBookId());
        }

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);

        if (shoppingCart.getCartItems().stream()
                .anyMatch(cart -> cart.getBook().getId().equals(requestDto.getBookId()))) {
            throw new DuplicateCartItemException("Duplicate cart item found");
        }

        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(bookRepository.getReferenceById(requestDto.getBookId()));
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    @Transactional
    public CartItemDto updateById(Long userId, Long id, UpdateCartItemRequestDto requestDto) {
        if (!cartItemRepository.existsById(id)) {
            throw new EntityNotFoundException("CartItem doesn't exist with id: " + id);
        }

        userComplianceCheck(userId, id);

        CartItem cartItem = cartItemRepository.getReferenceById(id);
        cartItem.setQuantity(requestDto.getQuantity());

        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    @Transactional
    public void deleteById(Long userId, Long cartItemId) {
        userComplianceCheck(userId, cartItemId);
        cartItemRepository.deleteById(cartItemId);
    }

    private void userComplianceCheck(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart.getCartItems().stream()
                .noneMatch(cart -> cart.getId().equals(cartItemId))) {
            throw new SecurityException("This user does not have access to this CartItem");
        }
    }
}
