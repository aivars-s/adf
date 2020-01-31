package adf.homework.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import adf.homework.domain.Product;
import adf.homework.dto.ProductDTO;
import adf.homework.exception.ResourceNotFoundException;
import adf.homework.mapper.ProductMapper;
import adf.homework.repository.ProductRepository;
import adf.homework.service.ProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductDTO dto) {
        return productMapper.toProductDTO(productRepository.save(productMapper.toProduct(dto)));
    }

    @Override
    public ProductDTO getProduct(long id) {
        return productMapper.toProductDTOFull(productRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    @Transactional
    public Product getProductDomainObject(long id) {
        return productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override public List<ProductDTO> getProducts() {
        return productMapper.toProductDTO(productRepository.findAll());
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO dto) {
        Product product = productRepository.findById(dto.getId()).orElseThrow(ResourceNotFoundException::new);
        productMapper.updateProduct(dto, product);
        return productMapper.toProductDTO(productRepository.save(product));
    }

}
