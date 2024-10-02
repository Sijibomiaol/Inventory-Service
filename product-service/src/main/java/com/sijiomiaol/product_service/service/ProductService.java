package com.sijiomiaol.product_service.service;

import com.sijiomiaol.product_service.dto.ProductRequest;
import com.sijiomiaol.product_service.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();


    ProductResponse updateProduct(UUID id, ProductRequest productRequest);

    void deleteProduct(UUID id);

    ProductResponse getProductById(UUID id);
}
