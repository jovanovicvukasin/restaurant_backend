package com.vukasin.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueReportDTO {

    private LocalDate fromDate;
    private LocalDate toDate;
    private Double totalRevenue;
    private Long totalOrders;
    private Double averageOrderValue;

}
