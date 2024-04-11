package store.bookshop.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import store.bookshop.config.MapperConfig;
import store.bookshop.dto.order.CreateOrderItemRequestDto;
import store.bookshop.dto.order.OrderItemDto;
import store.bookshop.model.OrderItem;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface OrderItemMapper {

    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);

    List<OrderItemDto> toDtoList(List<OrderItem> orderItemList);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookById")
    OrderItem toModel(CreateOrderItemRequestDto requestDto);

    List<OrderItem> toModelList(List<CreateOrderItemRequestDto> requestDtoList);
}
