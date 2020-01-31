package adf.homework.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import adf.homework.domain.DeliveryType;
import adf.homework.domain.PaymentType;
import adf.homework.dto.CustomerDTO;
import adf.homework.dto.OrderDTO;
import adf.homework.dto.ProductDTO;
import adf.homework.service.CustomerService;
import adf.homework.service.OrderService;
import adf.homework.service.ProductService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTests {

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderService orderService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderController controller;

    private List<OrderDTO> orders;
    private CustomerDTO customer;
    private List<ProductDTO> products;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        orders = new ArrayList<>();
        orders.add(OrderDTO.builder()
            .id(1L)
            .customerId(1L)
            .productId(1L)
            .createdAt(Instant.now())
            .unitPrice(BigDecimal.valueOf(100))
            .deliveryType(DeliveryType.DELIVER_TO_ADDRESS)
            .paymentType(PaymentType.CASH_ON_RECEIPT)
            .build());

        customer =
            CustomerDTO.builder()
                .id(1L)
                .firstName("Name")
                .lastName("Surname")
                .email("first@last@test.com")
                .phone("21234567")
                .build();

        products = new ArrayList<>();
        products.add(ProductDTO.builder()
                .id(1L)
                .category("Category")
                .manufacturer("Manufacturer")
                .name("Name")
                .remainingAmount(10)
                .unitPrice(BigDecimal.valueOf(100))
                .orders(new ArrayList<>())
                .discontinued(false)
                .build());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getOrders() throws Exception {
        when(orderService.getOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
            .andExpect(status().isOk())
            .andExpect(view().name("order/index"))
            .andExpect(model().attributeExists("orders"));

        verify(orderService, times(1)).getOrders();
    }

    @Test
    void getOrderCreateForm() throws Exception {
        when(customerService.getCustomers()).thenReturn(Collections.singletonList(customer));
        when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(get("/orders/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("order/form"))
            .andExpect(model().attributeExists("order", "customers", "products"));

        verify(customerService, times(1)).getCustomers();
        verify(productService, times(1)).getProducts();
    }

    @Test
    void postOrderCreateForm() throws Exception {
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(orders.get(0));

        mockMvc.perform(post("/orders/new")
            .param("customerId", String.valueOf(1))
            .param("productId", String.valueOf(1))
            .param("amount", String.valueOf(BigDecimal.valueOf(10)))
            .param("deliveryType", DeliveryType.DELIVER_TO_ADDRESS.toString())
            .param("paymentType", PaymentType.CASH_ON_RECEIPT.toString()))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/orders/1"))
            .andExpect(model().attributeExists("order"))
            .andExpect(model().attributeDoesNotExist("customers", "products"));

        verify(orderService, times(1)).createOrder(any(OrderDTO.class));
    }

    @Test
    void postOrderCreateForm_error() throws Exception {
        when(customerService.getCustomers()).thenReturn(Collections.singletonList(customer));
        when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(post("/orders/new")
            .param("customerId", String.valueOf(1))
            .param("productId", String.valueOf(1))
            .param("deliveryType", DeliveryType.DELIVER_TO_ADDRESS.toString())
            .param("paymentType", PaymentType.CASH_ON_RECEIPT.toString()))
            .andExpect(status().isOk())
            .andExpect(view().name("order/form"))
            .andExpect(model().attributeExists("order", "customers", "products"));
    }

    @Test
    void getOrder() throws Exception {
        when(orderService.getOrder(anyLong())).thenReturn(orders.get(0));

        mockMvc.perform(get("/orders/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("order/details"))
            .andExpect(model().attributeExists("order"));

        verify(orderService, times(1)).getOrder(anyLong());
    }

}
