package com.vukasin.restaurant.dto;

import com.vukasin.restaurant.model.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemDTO {

    private Long id;
    private String name;
    private ItemCategory itemCategory;
    private String description;
    private String imageUrl;

    private List<PriceItemDTO> priceItemDTOList = new ArrayList<>();

}
