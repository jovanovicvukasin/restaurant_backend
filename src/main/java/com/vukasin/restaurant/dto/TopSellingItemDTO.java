package com.vukasin.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSellingItemDTO {

    private LocalDate fromDate;
    private LocalDate toDate;
    private String itemName;
    private Long totalSold;
}
