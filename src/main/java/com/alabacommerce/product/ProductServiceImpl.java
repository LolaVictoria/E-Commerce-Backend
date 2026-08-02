/**
 * Contains the business logic for product management.
 *
 * Responsible for:
 * - Creating products
 * - Updating products
 * - Deleting products
 * - Searching products
 * - Validating product ownership
 */

package com.alabacommerce.product;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.alabacommerce.dto.ProductRequest;
import com.alabacommerce.dto.ProductResponse;
import com.alabacommerce.entity.Category;
import com.alabacommerce.entity.Product;
import com.alabacommerce.entity.User;
import com.alabacommerce.exception.ResourceNotFoundException;
import com.alabacommerce.service.CloudinaryService;
import com.alabacommerce.service.CurrentUserService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CurrentUserService currentUserService;
    private final CloudinaryService cloudinaryService;
    
    public ProductServiceImpl(
        ProductRepository productRepository,
        CurrentUserService currentUserService,
        CloudinaryService cloudinaryService) {
        
        this.productRepository = productRepository;
        this.currentUserService = currentUserService;
        this.cloudinaryService = cloudinaryService;
    }

    private void validateOwner(Product product, User user){
        if(!product.getSeller().getId().equals(user.getId())){
            throw new AccessDeniedException("You are not allowed...");
        }
    }
    


    @Override
    public ProductResponse createProduct(ProductRequest request) {

        User currentUser =
            currentUserService.getCurrentUser();
       
        Product product = mapToProduct(request);

        try {
            String imageUrl = cloudinaryService.uploadImage(request.getImage());
            product.setImageUrl(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }

        product.setSeller(currentUser);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    private Product mapToProduct(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());
       
        return product;
    }

    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setCategory(product.getCategory());
        response.setImageUrl(product.getImageUrl());
        if (product.getSeller() != null) {
            response.setSellerName(product.getSeller().getFirstName() + " " + product.getSeller().getLastName());
        } else {
            response.setSellerName("No Seller");
        }
        return response;
    }

    @Override
    public Page<ProductResponse> getAllProducts(
            Category category, 
            int page, 
            int size, 
            String sort,
            String keyword
        ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        
        if (keyword != null && !keyword.isBlank()) {

            if (category != null) {

                return productRepository
                    .findByCategoryAndNameContainingIgnoreCaseOrCategoryAndDescriptionContainingIgnoreCase(
                            category,
                            keyword,
                            category,
                            keyword,
                            pageable
                    )
                    .map(this::mapToResponse);

            } else {

                return productRepository
                    .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                            keyword,
                            keyword,
                            pageable
                    )
                    .map(this::mapToResponse);
            }
        } else {

            if (category != null) {

                return productRepository.findByCategory(
                    category, pageable
                ).map(this::mapToResponse);
            } else {
                return productRepository
                    .findAll(pageable)
                    .map(this::mapToResponse);
            }

         }
    
    }

    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return mapToResponse(product);
    }

    @Override
    public Page<ProductResponse> getAllSellersProducts(Category category, int page, int size, String sort, String keyword) {


    User currentUser = currentUserService.getCurrentUser();

        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        return productRepository
            .findBySeller(currentUser, pageable)
            .map(this::mapToResponse);
    }
    
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));


        User currentUser = currentUserService.getCurrentUser();

        validateOwner(existingProduct, currentUser);

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStock(request.getStock());
        existingProduct.setCategory(request.getCategory());
        try {
            String imageUrl = cloudinaryService.uploadImage(request.getImage());
            existingProduct.setImageUrl(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
        existingProduct.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(existingProduct);

        return mapToResponse(updatedProduct);
    }
 
    @Override
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));


        User currentUser = currentUserService.getCurrentUser();
        validateOwner(existingProduct, currentUser);

        
        productRepository.deleteById(id);   
    }
}
