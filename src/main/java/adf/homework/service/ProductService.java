package adf.homework.service;

import java.util.List;

import adf.homework.domain.Product;
import adf.homework.dto.ProductDTO;

public interface ProductService {

    ProductDTO createProduct(ProductDTO dto);
    ProductDTO getProduct(long id);
    Product getProductDomainObject(long id);
    List<ProductDTO> getProducts();
    ProductDTO updateProduct(ProductDTO dto);

}
