package com.alabacommerce.product;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.alabacommerce.dto.ProductRequest;
import com.alabacommerce.dto.ProductResponse;
import com.alabacommerce.entity.Category;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final PasswordEncoder passwordEncoder;
    private final ProductService productService;

    public ProductController(ProductService productService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/my-products")
    public Page<ProductResponse> getMyProducts(
            @RequestParam(required = false) Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
             @RequestParam(required = false) String keyword) {

        return productService.getAllSellersProducts(category, page, size, sort, keyword);
    }

    @GetMapping
    public Page<ProductResponse> getAllProducts(
        @RequestParam(required = false) Category category,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue =  "10") int size,
        @RequestParam(defaultValue = "createdAt") String sort,
        @RequestParam(required = false) String keyword) {
            return productService.getAllProducts(
                category,
                page,
                size,
                sort,
                keyword
            );
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(   
        @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}