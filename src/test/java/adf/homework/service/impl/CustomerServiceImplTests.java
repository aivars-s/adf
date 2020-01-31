package adf.homework.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import adf.homework.domain.Customer;
import adf.homework.dto.CustomerDTO;
import adf.homework.mapper.CustomerMapper;
import adf.homework.mapper.CustomerMapperImpl;
import adf.homework.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTests {

    @Spy
    private CustomerMapper customerMapper = new CustomerMapperImpl();

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private List<Customer> customers;

    @BeforeEach
    public void setUp() {
        customers = new ArrayList<>();

        customers.add(Customer.builder()
            .id(1L)
            .firstName("Name")
            .lastName("Surname")
            .email("first.last@test.com")
            .phone("21234567")
            .orders(new HashSet<>())
            .build());
    }

    @Test
    public void getCustomerDomainObject() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customers.get(0)));

        Customer customer = customerService.getCustomerDomainObject(1);

        assertEquals(customers.get(0), customer);

        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getCustomers() {
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.getCustomers();

        assertEquals(customers.size(), customerDTOS.size());

        verify(customerRepository, times(1)).findAll();
        verify(customerMapper, times(1)).toCustomerDTO(anyCollection());
    }

}
