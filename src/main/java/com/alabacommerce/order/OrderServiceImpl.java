/**
 * Handles customer checkout and order management.
 *
 * Responsible for:
 * - Checkout
 * - Viewing customer orders
 * - Viewing seller orders
 * - Updating order status
 */

package com.alabacommerce.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.alabacommerce.cart.CartRepository;
import com.alabacommerce.dto.OrderResponse;
import com.alabacommerce.dto.UpdateStatusRequest;
import com.alabacommerce.entity.Cart;
import com.alabacommerce.entity.CartItem;
import com.alabacommerce.entity.Order;
import com.alabacommerce.entity.OrderItem;
import com.alabacommerce.entity.OrderStatus;
import com.alabacommerce.entity.User;
import com.alabacommerce.exception.ResourceNotFoundException;
import com.alabacommerce.mapper.OrderMapper;
import com.alabacommerce.repository.OrderRepository;
import com.alabacommerce.repository.UserRepository;
import com.alabacommerce.service.CurrentUserService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final CurrentUserService currentUserService;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            UserRepository userRepository,
            OrderMapper orderMapper,
            CurrentUserService currentUserService) {

        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.orderMapper = orderMapper;
        this.currentUserService = currentUserService;
    }

    private Order findOrder(Long orderId) {
       return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));
    }

    

    @Override
    public OrderResponse checkout() {

        User user = currentUserService.getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Order order = new Order();

        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);

            orderItem.setProduct(cartItem.getProduct());

            orderItem.setQuantity(cartItem.getQuantity());

            orderItem.setPriceAtPurchase(cartItem.getPriceAtTime());

            order.getItems().add(orderItem);

            total = total.add(
                    cartItem.getPriceAtTime()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        // clear cart after checkout
        cart.getItems().clear();
        cartRepository.save(cart);

        return orderMapper.mapToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getOrders() {

        User user = currentUserService.getCurrentUser();

        return orderRepository.findByUser(user)
                .stream()
                .map(orderMapper::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrder(Long orderId) {

        User user = currentUserService.getCurrentUser();

        Order order = findOrder(orderId);

        if (!order.getUser().getId().equals(user.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("Access denied");
        }

        return orderMapper.mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getSellerOrders() {

        User seller = currentUserService.getCurrentUser();

        return orderRepository
                .findDistinctByItems_Product_Seller(seller)
                .stream()
                .map(orderMapper::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse updateStatus(
            Long orderId,
            UpdateStatusRequest request) {

        User seller = currentUserService.getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        boolean ownsProduct = order.getItems()
                .stream()
                .anyMatch(item ->
                        item.getProduct()
                                .getSeller()
                                .getId()
                                .equals(seller.getId()));

        if (!ownsProduct) {
            throw new org.springframework.security.access.AccessDeniedException("Access denied");
        }

        order.setStatus(request.getStatus());

        Order updated = orderRepository.save(order);

        return orderMapper.mapToResponse(updated);
    }

}