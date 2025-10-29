package com.vukasin.restaurant.service;

import com.vukasin.restaurant.dto.RevenueReportDTO;
import com.vukasin.restaurant.dto.TopSellingItemDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    RevenueReportDTO getRevenueReport(LocalDate fromDate, LocalDate toDate);

    List<TopSellingItemDTO> getTopSellingItems(LocalDate fromDate, LocalDate toDate);

}
