package com.vukasin.restaurant.service;

import com.vukasin.restaurant.dto.OrderDTO;
import com.vukasin.restaurant.model.RequestStatus;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    List<OrderDTO> getAllOrders();

    List<OrderDTO> getUserOrders();

    OrderDTO getOrderById(Long id);

    OrderDTO acceptOrder(Long orderId);
    OrderDTO cancelOrder(Long orderId);

}
