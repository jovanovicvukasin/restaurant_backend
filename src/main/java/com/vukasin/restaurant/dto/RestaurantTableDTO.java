package com.vukasin.restaurant.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantTableDTO {

    private Long id;

    @NotBlank(message = "Table number is required")
    private int tableNumber;

    @Min(value = 1, message = "table must have at least one seat")
    private int seats;
}
