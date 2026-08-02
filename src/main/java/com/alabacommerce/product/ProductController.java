/**
 * Handles product-related REST API endpoints.
 *
 * Features:
 * - Create products
 * - Retrieve products
 * - Update products
 * - Delete products
 * - Search and filter products
 *
 * Base URL: /products
 */

package com.alabacommerce.product;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.alabacommerce.dto.ProductRequest;
import com.alabacommerce.dto.ProductResponse;
import com.alabacommerce.entity.Category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Product", description = "Product management APIs")
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    @Operation(summary = "Create a new product")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/my-products")
    @Operation(summary = "Get my products")
    public Page<ProductResponse> getMyProducts(
            @RequestParam(required = false) Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
             @RequestParam(required = false) String keyword) {

        return productService.getAllSellersProducts(category, page, size, sort, keyword);
    }

    @GetMapping
    @Operation(summary = "Get all products")
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
    @Operation(summary = "Get a product by ID")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    @Operation(summary = "Update one of my products")
    public ProductResponse updateProduct(   
        @PathVariable Long id,
            @Valid @ModelAttribute ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    @Operation(summary = "Delete one of my products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
