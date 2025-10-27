package com.vukasin.restaurant.converter;

import com.vukasin.restaurant.dto.PriceItemDTO;
import com.vukasin.restaurant.model.PriceItem;
import org.springframework.stereotype.Component;

@Component()
public class PriceItemConverter {

    public PriceItemDTO toDTO(PriceItem entity) {
        if (entity == null) return null;

        return PriceItemDTO.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .validFrom(entity.getValidFrom())
                .validTo(entity.getValidTo())
                .menuItemId(entity.getMenuItem() != null ? entity.getMenuItem().getId() : null)
                .build();
    }

    public PriceItem toEntity(PriceItemDTO dto) {
        if (dto == null) return null;

        return PriceItem.builder()
                .id(dto.getId())
                .price(dto.getPrice())
                .currency(dto.getCurrency())
                .validFrom(dto.getValidFrom())
                .validTo(dto.getValidTo())
                // MenuItem i PriceList se postavljaju u servisu ako treba
                .build();
    }
}
