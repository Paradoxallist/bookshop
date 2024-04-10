package store.bookshop.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import store.bookshop.config.MapperConfig;
import store.bookshop.dto.shoppingcart.CartItemDto;
import store.bookshop.dto.shoppingcart.CreateCartItemRequestDto;
import store.bookshop.model.CartItem;

@Mapper(config = MapperConfig.class, uses = {BookMapper.class})
public interface CartItemMapper {

    @Mapping(target = "bookId", source = "book", qualifiedByName = "idByBook")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "shoppingCart", ignore = true)
    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookById")
    CartItem toModel(CreateCartItemRequestDto requestDto);

    default List<CartItemDto> toCartItemsDto(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
