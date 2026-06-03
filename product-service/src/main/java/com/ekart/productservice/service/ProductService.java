package com.ekart.productservice.service;

import com.ekart.productservice.dto.ProductRequestDto;
import com.ekart.productservice.dto.ProductResponseDto;


import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto dto);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(Long id);

    ProductResponseDto updateProduct(Long id, ProductRequestDto dto);

    void deleteProduct(Long id);
}