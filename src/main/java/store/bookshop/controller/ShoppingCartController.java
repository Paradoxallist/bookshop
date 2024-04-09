package store.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import store.bookshop.dto.shoppingcart.CartItemDto;
import store.bookshop.dto.shoppingcart.CreateCartItemRequestDto;
import store.bookshop.dto.shoppingcart.ShoppingCartDto;
import store.bookshop.dto.shoppingcart.UpdateCartItemRequestDto;
import store.bookshop.model.User;
import store.bookshop.service.shoppingcart.ShoppingCartService;

@Tag(name = "Shopping cart manager", description = "Endpoint for managing shopping cart")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get shopping cart user")
    public ShoppingCartDto getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.findByUserId(user.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a book to the shopping cart.")
    public CartItemDto addShoppingCart(@RequestBody CreateCartItemRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.save(user.getId(), requestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update category by id")
    public CartItemDto updateShoppingCartById(@PathVariable("cartItemId") Long id,
                                              @RequestBody UpdateCartItemRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateById(user.getId(), id, requestDto);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update category by id")
    public void deleteShoppingCartById(@PathVariable("cartItemId") Long cartItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteById(user.getId(), cartItemId);
    }
}
