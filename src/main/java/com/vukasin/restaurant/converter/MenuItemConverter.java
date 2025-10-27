package com.vukasin.restaurant.converter;

import com.vukasin.restaurant.dto.MenuItemDTO;
import com.vukasin.restaurant.dto.PriceItemDTO;
import com.vukasin.restaurant.model.MenuItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuItemConverter {

    private final PriceItemConverter priceItemConverter;

    public MenuItemConverter(PriceItemConverter priceItemConverter) {
        this.priceItemConverter = priceItemConverter;
    }

    public MenuItemDTO toDTO(MenuItem menuItem) {
        if (menuItem == null) return null;

        return MenuItemDTO.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .itemCategory(menuItem.getItemCategory())
                .imageUrl(menuItem.getImageUrl())
                .priceItemDTOList(menuItem.getPrices().stream()
                        .map(priceItemConverter::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public MenuItem toEntity(MenuItemDTO dto) {
        if (dto == null) return null;

        MenuItem menuItem = MenuItem.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .itemCategory(dto.getItemCategory())
                .imageUrl(dto.getImageUrl())
                .build();

        if(dto.getPriceItemDTOList() != null) {
            menuItem.setPrices(dto.getPriceItemDTOList().stream()
                    .map(priceItemConverter::toEntity)
                    .collect(Collectors.toList()));
        }

        return menuItem;
    }


}
