package com.vukasin.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemInfoDTO {

    private Long id;
    private String name;
    private String description;
}
