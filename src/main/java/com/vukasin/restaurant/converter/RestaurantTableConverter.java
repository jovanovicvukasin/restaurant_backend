package com.vukasin.restaurant.converter;

import com.vukasin.restaurant.dto.RestaurantTableDTO;
import com.vukasin.restaurant.model.RestaurantTable;
import org.springframework.stereotype.Component;

@Component
public class RestaurantTableConverter {

    public RestaurantTableDTO toDTO(RestaurantTable entity) {
        if (entity == null) return null;

        return RestaurantTableDTO.builder()
                .id(entity.getId())
                .tableNumber(entity.getTableNumber())
                .seats(entity.getSeats())
                .build();
    }

    public RestaurantTable toEntity(RestaurantTableDTO dto) {
        if (dto == null) return null;

        RestaurantTable table = new RestaurantTable();
        table.setId(dto.getId());
        table.setTableNumber(dto.getTableNumber());
        table.setSeats(dto.getSeats());
        return table;
    }
}
