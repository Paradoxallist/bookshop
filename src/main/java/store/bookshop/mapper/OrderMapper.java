package store.bookshop.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import store.bookshop.config.MapperConfig;
import store.bookshop.dto.order.OrderDto;
import store.bookshop.model.Order;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> books);
}
