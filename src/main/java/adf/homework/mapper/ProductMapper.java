package adf.homework.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import adf.homework.domain.Product;
import adf.homework.dto.ProductDTO;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface ProductMapper {

    @Mapping(target = "orders", ignore = true)
    @Named("Regular")
    ProductDTO toProductDTO(Product source);

    @IterableMapping(qualifiedByName = "Regular")
    @Named("Regular")
    List<ProductDTO> toProductDTO(Collection<Product> source);

    @Mapping(target = "orders", qualifiedByName = "Regular")
    @Named("Full")
    ProductDTO toProductDTOFull(Product source);

    @Mapping(target = "orders", ignore = true)
    Product toProduct(ProductDTO source);

    @Mapping(target = "orders", ignore = true)
    void updateProduct(ProductDTO source, @MappingTarget Product target);

}
