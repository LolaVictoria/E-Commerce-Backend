package com.alabacommerce.product;

import org.springframework.data.domain.Page;
import com.alabacommerce.dto.ProductRequest;
import com.alabacommerce.dto.ProductResponse;
import com.alabacommerce.entity.Category;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);
    Page<ProductResponse> getAllProducts(
        Category category,
        int page, 
        int size, 
        String sort,
        String keyword
    );  
    Page<ProductResponse> getAllSellersProducts(Category category, int page, int size, String sort, String keyword);
    ProductResponse getProductById(Long id);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
}