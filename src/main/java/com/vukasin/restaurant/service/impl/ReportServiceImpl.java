package com.vukasin.restaurant.service.impl;

import com.vukasin.restaurant.dto.RevenueReportDTO;
import com.vukasin.restaurant.dto.TopSellingItemDTO;
import com.vukasin.restaurant.repository.OrderRepository;
import com.vukasin.restaurant.repository.ReportRepository;
import com.vukasin.restaurant.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public RevenueReportDTO getRevenueReport(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = toDate.plusDays(1).atStartOfDay();

        Double totalRevenue = reportRepository.calculateTotalRevenue(from, to);
        Long totalOrders = reportRepository.countOrders(from, to);

        if (totalRevenue == null) {
            totalRevenue = 0.0;
        }
        if(totalOrders == null) {
            totalOrders = 0L;
        }

        double average = totalOrders > 0 ? totalRevenue / totalOrders : 0.0;

        return new RevenueReportDTO(fromDate, toDate.minusDays(1), totalRevenue, totalOrders, average);
    }

    @Override
    public List<TopSellingItemDTO> getTopSellingItems(LocalDate fromDate, LocalDate toDate) {

        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = toDate.plusDays(1).atStartOfDay();

        List<Object []> topItems = reportRepository.findTopSellingItems(from, to);
        List<TopSellingItemDTO> topSellingItems = new ArrayList<>();

        for(Object[] item : topItems) {
            String itemName = (String) item[0];
            Long totalSold = (Long) item[1];
            topSellingItems.add(new TopSellingItemDTO(fromDate, toDate.minusDays(1), itemName, totalSold));
        }

        return topSellingItems;
    }
}
