package adf.homework.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import adf.homework.domain.Customer;
import adf.homework.domain.DeliveryType;
import adf.homework.domain.Order;
import adf.homework.domain.PaymentType;
import adf.homework.domain.Product;
import adf.homework.dto.OrderDTO;
import adf.homework.mapper.CustomerMapper;
import adf.homework.mapper.CustomerMapperImpl;
import adf.homework.mapper.OrderMapper;
import adf.homework.mapper.OrderMapperImpl;
import adf.homework.mapper.ProductMapper;
import adf.homework.mapper.ProductMapperImpl;
import adf.homework.repository.OrderRepository;
import adf.homework.service.CustomerService;
import adf.homework.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTests {

    @Spy
    private CustomerMapper customerMapper = new CustomerMapperImpl();

    @Spy
    private ProductMapper productMapper = new ProductMapperImpl();

    @Spy
    @InjectMocks
    private OrderMapper orderMapper = new OrderMapperImpl();

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductService productService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Customer customer;
    private Product product;
    private List<Order> orders;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder()
            .id(1L)
            .firstName("Name")
            .lastName("Surname")
            .email("first.last@test.com")
            .phone("21234567")
            .orders(new HashSet<>())
            .build();

        product = Product.builder()
            .id(1L)
            .category("Category")
            .manufacturer("Manufacturer")
            .name("Name")
            .remainingAmount(10)
            .unitPrice(BigDecimal.valueOf(100))
            .discontinued(false)
            .orders(new HashSet<>())
            .build();

        orders = new ArrayList<>();
        orders.add(Order.builder()
            .id(1L)
            .customer(customer)
            .product(product)
            .amount(1)
            .unitPrice(product.getUnitPrice())
            .deliveryType(DeliveryType.DELIVER_TO_ADDRESS)
            .paymentType(PaymentType.CASH_ON_RECEIPT)
            .build());
    }

    @Test
    public void createOrder() {
        when(customerService.getCustomerDomainObject(anyLong())).thenReturn(customer);
        when(productService.getProductDomainObject(anyLong())).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenReturn(orders.get(0));

        OrderDTO dto = OrderDTO.builder()
            .id(1L)
            .customerId(1L)
            .productId(1L)
            .amount(1)
            .deliveryType(DeliveryType.DELIVER_TO_ADDRESS)
            .paymentType(PaymentType.CASH_ON_RECEIPT)
            .build();

        OrderDTO createdDTO = orderService.createOrder(dto);

        verify(customerService, times(1)).getCustomerDomainObject(anyLong());
        verify(productService, times(1)).getProductDomainObject(anyLong());
        verify(orderRepository, times(1)).save(any(Order.class));

        assertTrue(new ReflectionEquals(orderMapper.toOrderDTO(orders.get(0))).matches(createdDTO));
    }

    @Test
    public void getOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orders.get(0)));

        orderService.getOrder(1);

        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getOrders() {
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDTO> dtos = orderService.getOrders();

        assertEquals(orders.size(), dtos.size());

        verify(orderRepository, times(1)).findAll();
    }

}
