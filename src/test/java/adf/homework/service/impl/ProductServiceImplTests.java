package adf.homework.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import adf.homework.domain.Product;
import adf.homework.dto.ProductDTO;
import adf.homework.mapper.OrderMapper;
import adf.homework.mapper.OrderMapperImpl;
import adf.homework.mapper.ProductMapper;
import adf.homework.mapper.ProductMapperImpl;
import adf.homework.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTests {

    @Spy
    private OrderMapper orderMapper =  new OrderMapperImpl();

    @Spy
    @InjectMocks
    private ProductMapper productMapper = new ProductMapperImpl();

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private List<Product> products;
    private Product product;
    private ProductDTO dto;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
            .id(1L)
            .category("Category")
            .manufacturer("Manufaturer")
            .name("Name")
            .remainingAmount(10)
            .unitPrice(BigDecimal.valueOf(100))
            .discontinued(false)
        .build();

        products = new ArrayList<>();
        products.add(product);

        dto = ProductDTO.builder()
            .id(1L)
            .category("Category")
            .manufacturer("Manufaturer")
            .name("Name")
            .remainingAmount(10)
            .unitPrice(BigDecimal.valueOf(100))
            .discontinued(false)
            .orders(new ArrayList<>())
            .build();
    }

    @Test
    public void createProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO productDTO = productService.createProduct(dto);

        assertTrue(new ReflectionEquals(productMapper.toProductDTO(product)).matches(productDTO));

        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toProductDTO(any(Product.class));
    }

    @Test
    public void getProducts() {
        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> result = productService.getProducts();

        assertEquals(products.size(), result.size());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getProduct() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductDTO productDTO = productService.getProduct(1);

        assertTrue(new ReflectionEquals(productMapper.toProductDTO(product)).matches(productDTO));

        verify(productRepository, times(1)).findById(anyLong());
        verify(productMapper, times(1)).toProductDTO(any(Product.class));
    }

    @Test
    public void getProductDomainObject() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product result = productService.getProductDomainObject(1);

        assertTrue(new ReflectionEquals(product).matches(result));

        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void updateProduct() {
        when(productRepository.findById(dto.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO productDTO = productService.updateProduct(dto);

        assertTrue(new ReflectionEquals(productMapper.toProductDTO(product)).matches(productDTO));

        verify(productRepository, times(1)).findById(dto.getId());
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toProductDTO(any(Product.class));
    }

}
