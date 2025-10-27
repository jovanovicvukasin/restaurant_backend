package com.vukasin.restaurant.converter;

import com.vukasin.restaurant.dto.OrderItemDTO;
import com.vukasin.restaurant.model.MenuItem;
import com.vukasin.restaurant.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {

    public OrderItemDTO toDTO(OrderItem entity) {
        if (entity == null) return null;

        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setMenuItemId(entity.getMenuItem() != null ? entity.getMenuItem().getId() : null);
        dto.setOrderId(entity.getOrder() != null ? entity.getOrder().getId() : null);
        return dto;
    }

    public OrderItem toEntity(OrderItemDTO dto, MenuItem menuItem) {
        if (dto == null) return null;

        OrderItem entity = new OrderItem();
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setMenuItem(menuItem);
        return entity;
    }
}
