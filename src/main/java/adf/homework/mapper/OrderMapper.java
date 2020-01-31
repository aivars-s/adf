package adf.homework.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import adf.homework.domain.Order;
import adf.homework.dto.OrderDTO;

@Mapper(componentModel = "spring", uses = { CustomerMapper.class, ProductMapper.class })
public interface OrderMapper {

    @Mapping(target = "customerId", source = "source.customer.id")
    @Mapping(target = "productId", source = "source.product.id")
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Named("Regular")
    OrderDTO toOrderDTO(Order source);

    @IterableMapping(qualifiedByName = "Regular")
    @Named("Regular")
    List<OrderDTO> toOrderDTO(Collection<Order> source);

    @Mapping(target = "customer", qualifiedByName = "Regular")
    @Mapping(target = "product", qualifiedByName = "Regular")
    @Named("Full")
    OrderDTO toOrderDTOFull(Order source);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "product", ignore = true)
    Order toOrder(OrderDTO source);

}
