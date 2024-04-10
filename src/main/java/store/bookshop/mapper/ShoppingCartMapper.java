package store.bookshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import store.bookshop.config.MapperConfig;
import store.bookshop.dto.shoppingcart.ShoppingCartDto;
import store.bookshop.model.ShoppingCart;

@Mapper(config = MapperConfig.class, uses = {CartItemMapper.class})
public interface ShoppingCartMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItems")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

}
