package com.vukasin.restaurant.converter;

import com.vukasin.restaurant.dto.MenuItemDTO;
import com.vukasin.restaurant.dto.OrderDTO;
import com.vukasin.restaurant.dto.OrderItemDTO;
import com.vukasin.restaurant.model.MenuItem;
import com.vukasin.restaurant.model.Order;
import com.vukasin.restaurant.model.OrderItem;
import com.vukasin.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter {

    @Autowired
    private OrderItemConverter orderItemConverter;
    @Autowired
    private MenuItemConverter menuItemConverter;
    @Autowired
    private MenuItemRepository menuItemRepository;

    public OrderDTO toDTO(Order entity) {
        if (entity == null) return null;

        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        if (entity.getOrderItems() != null) {
            for (OrderItem orderItem : entity.getOrderItems()) {
                orderItemDTOs.add(orderItemConverter.toDTO(orderItem));
            }
        }

        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setDateAndTime(entity.getOrderDateTime());
        dto.setOrderStatus(entity.getOrderStatus());
        dto.setOrderType(entity.getOrderType());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        dto.setOrderItems(orderItemDTOs);

        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        if (dto == null) return null;

        Order entity = new Order();
        entity.setId(dto.getId());
        entity.setOrderDateTime(dto.getDateAndTime());
        entity.setOrderStatus(dto.getOrderStatus());
        entity.setOrderType(dto.getOrderType());
        entity.setTotalAmount(dto.getTotalAmount());

        if (dto.getOrderItems() != null) {
            for (OrderItemDTO orderItemDTO : dto.getOrderItems()) {
                MenuItem menuItem = menuItemRepository.findById(orderItemDTO.getMenuItemId())
                        .orElseThrow(() -> new IllegalArgumentException("Menu item not found."));
                OrderItem orderItem = orderItemConverter.toEntity(orderItemDTO, menuItem);
                entity.addOrderItem(orderItem);

            }
        }

        return entity;
    }
}
