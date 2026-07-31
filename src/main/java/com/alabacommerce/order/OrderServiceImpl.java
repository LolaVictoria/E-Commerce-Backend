package com.alabacommerce.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
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

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            UserRepository userRepository,
            OrderMapper orderMapper) {

        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email);
    }

    @Override
    public OrderResponse checkout() {

        User user = getCurrentUser();

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

        User user = getCurrentUser();

        return orderRepository.findByUser(user)
                .stream()
                .map(orderMapper::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrder(Long orderId) {

        User user = getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return orderMapper.mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getSellerOrders() {

        User seller = getCurrentUser();

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

        User seller = getCurrentUser();

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
            throw new RuntimeException("Access denied");
        }

        order.setStatus(request.getStatus());

        Order updated = orderRepository.save(order);

        return orderMapper.mapToResponse(updated);
    }

}