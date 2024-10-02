package com.sijiomiaol.product_service.controller;

import com.sijiomiaol.product_service.dto.ApiResponse;
import com.sijiomiaol.product_service.dto.ProductRequest;
import com.sijiomiaol.product_service.dto.ProductResponse;
import com.sijiomiaol.product_service.exception.ResourceNotFoundException;
import com.sijiomiaol.product_service.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productservice;
    @PostMapping("/create-product")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest productRequest) {
        try{
                ProductResponse productResponse = productservice.createProduct(productRequest);
                return new ResponseEntity<>(new ApiResponse<>(true, "Product created successfuly", productResponse), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable UUID id, @RequestBody ProductRequest productRequest) {
        try {
            ProductResponse updatedProduct = productservice.updateProduct(id, productRequest);

            return new ResponseEntity<>(new ApiResponse<>(true, "Product updated successfuly", updatedProduct), HttpStatus.OK);

        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(new ApiResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> deleteProduct(@PathVariable UUID id) {
        try {
            productservice.deleteProduct(id);
            return new ResponseEntity<>(new ApiResponse<>(true,"Product is successfully deleted", null ), HttpStatus.OK);
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(new ApiResponse<>(false, "Product has not found", null ), HttpStatus.NOT_FOUND);
        }
        catch (Exception e ){
            return new ResponseEntity<>(new ApiResponse<>(false, "An error occured", null), HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/all-products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        try {
            List<ProductResponse> productResponseList = productservice.getAllProducts();
            return new ResponseEntity<>(new ApiResponse<>(true, " All Product retrived", productResponseList), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse<>(false, "An error occur while retrieving the product", null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable UUID id) {
        log.info("Fetching product ID: {}", id);
        try {
            ProductResponse productResponse = productservice.getProductById(id);
            return new ResponseEntity<>(new ApiResponse<>(true, " Product retrieved", productResponse),
                    HttpStatus.OK);
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(new ApiResponse<>(false, "Product not Found", null), HttpStatus.NOT_FOUND);

        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse<>(false, "An Error occur while fetching the product", null), HttpStatus.BAD_REQUEST);
        }
    }
}
