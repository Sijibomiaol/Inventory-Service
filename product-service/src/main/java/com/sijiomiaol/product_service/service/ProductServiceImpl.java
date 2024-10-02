package com.sijiomiaol.product_service.service;

import com.sijiomiaol.product_service.dto.ProductRequest;
import com.sijiomiaol.product_service.dto.ProductResponse;
import com.sijiomiaol.product_service.entity.Product;
import com.sijiomiaol.product_service.exception.ResourceNotFoundException;
import com.sijiomiaol.product_service.repository.ProductRepository;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product {} created", product.getId());
        return modelMapper.map(product, ProductResponse.class);

    }

    @Override
    public List<ProductResponse> getAllProducts() {

        List<Product> allProducts = productRepository.findAll();
        List<ProductResponse> responseList = allProducts.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class)).collect(Collectors.toList());
        return responseList;
    }

//    public ProductRequest updateProduct(UUID id, Map<String, Object> update) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(()->
//                        new ResourceNotFoundException("Product not found with ID" + id));
//        update.forEach((key, value) ->{
//            switch (key){
//                case "name":
//                    product.setName((String) value);
//                    break;
//                case "description":
//                    product.setDescription((String) value);
//                    break;
//
//                case "price":
//                    product.setPrice((BigDecimal) value);
//                    break;
//            }
//        });
//        return productRepository.save(product);
//    }


    @Override
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (productRequest.getName() != null) {
            product.setName(productRequest.getName());
        }
        if (productRequest.getDescription() != null) {
            product.setDescription(productRequest.getDescription());
        }
        if (productRequest.getPrice() != null) {
            product.setPrice(productRequest.getPrice());
        }
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductResponse.class);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.deleteById(id);
        log.info("Product {} deleted", product.getId());
    }
    @Override
    public ProductResponse getProductById(UUID id) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
            return productResponse;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Product not found" + id);
        }
        catch (RuntimeException e) {
            throw new InternalServerErrorException("Unexpected Error occured while fetching product");
    }
}
    
}
