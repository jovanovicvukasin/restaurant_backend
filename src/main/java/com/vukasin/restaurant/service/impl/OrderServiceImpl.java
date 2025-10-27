package com.vukasin.restaurant.service.impl;

import com.vukasin.restaurant.converter.OrderConverter;
import com.vukasin.restaurant.converter.OrderItemConverter;
import com.vukasin.restaurant.dto.OrderDTO;
import com.vukasin.restaurant.dto.OrderItemDTO;
import com.vukasin.restaurant.model.*;
import com.vukasin.restaurant.repository.MenuItemRepository;
import com.vukasin.restaurant.repository.OrderRepository;
import com.vukasin.restaurant.repository.UserRepository;
import com.vukasin.restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderConverter orderConverter;
    private final OrderItemConverter orderItemConverter;
    private final UserRepository userRepository;


    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(RequestStatus.NEW);

        if(orderDTO.getOrderType() != null){
            order.setOrderType(orderDTO.getOrderType());
        }

        double totalAmount = 0.0;
        if(orderDTO.getOrderItems() == null || orderDTO.getOrderItems().isEmpty()){
            throw new RuntimeException("Order must contain at least one item.");
        }

        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            MenuItem menuItem = menuItemRepository.findById(orderItemDTO.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("MenuItem not found"));


            Double unitPrice;
            try {
                unitPrice = getActivePrice(menuItem);
            }catch (RuntimeException e){
                throw new RuntimeException("Menu item " + menuItem.getName() + " cannot be ordered (no active price).");
            }

            int quantity = orderItemDTO.getQuantity();
            if(quantity <= 0){
                throw new RuntimeException("Quantity must be greater than zero.");
            }

            double totalPrice = unitPrice * quantity;

            OrderItem orderItem = orderItemConverter.toEntity(orderItemDTO, menuItem);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setTotalPrice(totalPrice);
            order.addOrderItem(orderItem);

            totalAmount += totalPrice;

        }

        if(orderDTO.getOrderType().equals(OrderType.DELIVERY)){
            totalAmount += 350.0;
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        return orderConverter.toDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order o : orders) {
            orderDTOs.add(orderConverter.toDTO(o));
        }
        return orderDTOs;
    }

    @Override
    public List<OrderDTO> getUserOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findAllByUser(user);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order o : orders) {
            orderDTOs.add(orderConverter.toDTO(o));
        }
        return orderDTOs;
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderConverter.toDTO(order);
    }

    @Override
    public OrderDTO acceptOrder(Long orderId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(!user.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("You do not have permission to accept this order. Only admin can accept orders.");
        }

        if(order.getOrderStatus() == RequestStatus.ACCEPTED){
            throw new RuntimeException("Order is already accepted.");
        }
        if(order.getOrderStatus() == RequestStatus.CANCELED){
            throw new RuntimeException("Cannot accept a canceled order.");
        }

        order.setOrderStatus(RequestStatus.ACCEPTED);
        orderRepository.save(order);

        return orderConverter.toDTO(order);
    }

    @Override
    public OrderDTO cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getOrderStatus() == RequestStatus.ACCEPTED){
            throw new RuntimeException("Order is already accepted and cannot be cancelled.");
        }
        if (order.getOrderStatus() == RequestStatus.CANCELED) {
            throw new RuntimeException("Order is already canceled.");
        }

        order.setOrderStatus(RequestStatus.CANCELED);
        orderRepository.save(order);

        return orderConverter.toDTO(order);
    }

    private Double getActivePrice(MenuItem menuItem) {
        LocalDate today = LocalDate.now();

        List<PriceItem> priceItems = menuItem.getPrices();
        for (PriceItem price : priceItems) {
            boolean validFrom = (price.getValidFrom() == null || !today.isBefore(price.getValidFrom()));
            boolean validTo = (price.getValidTo() == null || !today.isAfter(price.getValidTo()));
            if (validFrom && validTo) {
                return price.getPrice();
            }
        }
        throw new RuntimeException("No active price found for menu item: " + menuItem.getName());
    }
}
