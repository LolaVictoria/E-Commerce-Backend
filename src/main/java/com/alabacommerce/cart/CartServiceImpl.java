/**
 * Handles shopping cart operations.
 *
 * Responsible for:
 * - Adding items
 * - Updating quantities
 * - Removing items
 * - Clearing the cart
 * - Returning the current user's cart
 */


package com.alabacommerce.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.alabacommerce.dto.CartItemRequest;
import com.alabacommerce.dto.CartItemResponse;
import com.alabacommerce.dto.CartResponse;
import com.alabacommerce.entity.Cart;
import com.alabacommerce.entity.CartItem;
import com.alabacommerce.entity.Product;
import com.alabacommerce.entity.User;
import com.alabacommerce.exception.ResourceNotFoundException;
import com.alabacommerce.mapper.ProductMapper;
import com.alabacommerce.product.ProductRepository;
import com.alabacommerce.service.CurrentUserService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CurrentUserService currentUserService;
    public CartServiceImpl(
        CartRepository cartRepository,
        CartItemRepository cartItemRepository,
        ProductRepository productRepository,
        ProductMapper productMapper,
        CurrentUserService currentUserService) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.currentUserService = currentUserService;
    }

    private CartResponse mapCartResponse(Cart cart) {

        CartResponse response = new CartResponse();

        response.setId(cart.getId());

        List<CartItemResponse> itemResponses = cart.getItems()
                .stream()
                .map(this::mapCartItemResponse)
                .toList();

        response.setItems(itemResponses);

        BigDecimal total = itemResponses.stream()
                .map(item ->
                        item.getPriceAtTime()
                            .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        response.setTotalPrice(total);

        return response;
    }

    private Cart getOrCreateCart(User user) {

    return cartRepository.findByUser(user)
            .orElseGet(() -> {

                Cart cart = new Cart();

                cart.setUser(user);
                cart.setCreatedAt(LocalDateTime.now());
                cart.setUpdatedAt(LocalDateTime.now());

                return cartRepository.save(cart);
            });
}

    private CartItemResponse mapCartItemResponse(CartItem item) {

        CartItemResponse response = new CartItemResponse();

        response.setId(item.getId());
        response.setQuantity(item.getQuantity());
        response.setPriceAtTime(item.getPriceAtTime());

        response.setProduct(
                productMapper.mapToResponse(item.getProduct()));

        return response;
    }
    

    @Override
    public CartResponse addItem(CartItemRequest request) {
        //step 1 - find logged in user
        //step 2: find product
        //step 3 - Does user have cart?
        //step 4 - if no - Create cart
        //step 5 - Does cart already contain item e.g Laptop?
        //step 6-  No - Create CartItem
        //step 7 - return updated cart
        User user = currentUserService.getCurrentUser();
    
        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() ->
                    new ResourceNotFoundException("Product not found"));
        
        Cart cart = getOrCreateCart(user);
        Optional<CartItem> existingItem =
            cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();

            item.setQuantity(
                    item.getQuantity() + request.getQuantity());

            item.setUpdatedAt(LocalDateTime.now());

            cartItemRepository.save(item);

        } else {

            CartItem item = new CartItem();

            item.setCart(cart);

            item.setProduct(product);

            item.setQuantity(request.getQuantity());

            item.setPriceAtTime(product.getPrice());

            item.setUpdatedAt(LocalDateTime.now());

            cartItemRepository.save(item);
        }

        return mapCartResponse(cart);
    }

    

    @Override
    public CartResponse getCart() {
        User user = currentUserService.getCurrentUser();

        Cart cart = getOrCreateCart(user);
        CartResponse response = mapCartResponse(cart);

        return response;
    }    
    
    
    @Override
    public CartResponse updateItem(Long cartItemId, CartItemRequest request) {

        User user = currentUserService.getCurrentUser();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Not your cart");
        }

        cartItem.setQuantity(request.getQuantity());
        cartItem.setUpdatedAt(LocalDateTime.now());

        cartItemRepository.save(cartItem);

        return mapCartResponse(cartItem.getCart());
    }
    
    @Override
    public void removeItem(Long cartItemId) {

        User user = currentUserService.getCurrentUser();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Not your cart");
        }

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart() {

        User user = currentUserService.getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        cart.getItems().clear();

        cartRepository.save(cart);
    }
    
}
