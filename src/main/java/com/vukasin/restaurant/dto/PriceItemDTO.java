package com.vukasin.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceItemDTO {

    private Long id;
    private Double price;
    private String currency;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Long menuItemId;
}
