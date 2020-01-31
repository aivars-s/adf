package adf.homework.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import adf.homework.domain.Customer;
import adf.homework.dto.CustomerDTO;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "orders", ignore = true)
    @Named("Regular")
    CustomerDTO toCustomerDTO(Customer source);

    @IterableMapping(qualifiedByName = "Regular")
    @Named("Regular")
    List<CustomerDTO> toCustomerDTO(Collection<Customer> source);

}
