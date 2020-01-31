package adf.homework.controller;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import adf.homework.dto.ProductDTO;
import adf.homework.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO product;
    private List<ProductDTO> products;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        product = ProductDTO.builder()
            .id(1L)
            .category("Category")
            .manufacturer("Manufacturer")
            .name("Name")
            .remainingAmount(10)
            .unitPrice(BigDecimal.valueOf(100))
            .orders(new ArrayList<>())
            .discontinued(false)
            .build();

        products = new ArrayList<>();
        products.add(product);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void getProducts() throws Exception {
        when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(view().name("product/index"))
            .andExpect(model().attributeExists("products"));
    }

    @Test
    public void getProductCreateForm() throws Exception {
        mockMvc.perform(get("/products/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("product/form"))
            .andExpect(model().attributeExists("product"));
    }

    @Test
    public void postProductCreateForm() throws Exception {
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);

        mockMvc.perform(post("/products/new")
            .param("category", "Category")
            .param("manufacturer", "Manufacturer")
            .param("name", "Name")
            .param("remainingAmount", String.valueOf(10))
            .param("unitPrice", String.valueOf(BigDecimal.valueOf(100)))
            .param("discontinued", String.valueOf(false)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/products/1"));

        verify(productService, times(1)).createProduct(any(ProductDTO.class));
    }

    @Test
    public void postProductCreateForm_error() throws Exception {
        mockMvc.perform(post("/products/new")
            .param("category", "Category")
            .param("manufacturer", "Manufacturer")
            .param("remainingAmount", String.valueOf(10))
            .param("unitPrice", String.valueOf(BigDecimal.valueOf(100)))
            .param("discontinued", String.valueOf(false)))
            .andExpect(status().isOk())
            .andExpect(view().name("product/form"));
    }

    @Test
    public void getProduct() throws Exception {
        when(productService.getProduct(anyLong())).thenReturn(product);

        mockMvc.perform(get("/products/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("product/details"))
            .andExpect(model().attributeExists("product"));
    }

    @Test
    public void getProductUpdateForm() throws Exception {
        when(productService.getProduct(anyLong())).thenReturn(product);

        mockMvc.perform(get("/products/1/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("product/form"))
            .andExpect(model().attributeExists("product"));
    }

    @Test
    public void postProductUpdateForm() throws Exception {
        when(productService.updateProduct(any(ProductDTO.class))).thenReturn(product);

        mockMvc.perform(post("/products/1/edit")
            .param("id", String.valueOf(1))
            .param("category", "Category")
            .param("manufacturer", "Manufacturer")
            .param("name", "Name")
            .param("remainingAmount", String.valueOf(10))
            .param("unitPrice", String.valueOf(BigDecimal.valueOf(100)))
            .param("discontinued", String.valueOf(false)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/products/1"));

        verify(productService, times(1)).updateProduct(any(ProductDTO.class));
    }

    @Test
    public void postProductUpdateForm_error() throws Exception {
        mockMvc.perform(post("/products/1/edit")
            .param("id", String.valueOf(1))
            .param("category", "Category")
            .param("manufacturer", "Manufacturer")
            .param("remainingAmount", String.valueOf(10))
            .param("unitPrice", String.valueOf(BigDecimal.valueOf(100)))
            .param("discontinued", String.valueOf(false)))
            .andExpect(status().isOk())
            .andExpect(view().name("product/form"));
    }

}
