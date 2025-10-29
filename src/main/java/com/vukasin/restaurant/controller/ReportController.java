package com.vukasin.restaurant.controller;

import com.vukasin.restaurant.dto.RevenueReportDTO;
import com.vukasin.restaurant.dto.TopSellingItemDTO;
import com.vukasin.restaurant.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/revenue")
    public ResponseEntity<RevenueReportDTO> getRevenueReport(@RequestBody RevenueReportDTO revenueReportDTO) {
        if (revenueReportDTO.getFromDate().isAfter(revenueReportDTO.getToDate())) {
            return ResponseEntity.badRequest().build();
        }
        RevenueReportDTO dto = reportService.getRevenueReport(revenueReportDTO.getFromDate(), revenueReportDTO.getToDate());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/top-items")
    public ResponseEntity<List<TopSellingItemDTO>> getTopSellingItems(@RequestBody TopSellingItemDTO topSellingItemDTO) {

        if (topSellingItemDTO.getFromDate().isAfter(topSellingItemDTO.getToDate())) {
            return ResponseEntity.badRequest().build();
        }
        List<TopSellingItemDTO> topItems = reportService.getTopSellingItems(topSellingItemDTO.getFromDate(), topSellingItemDTO.getToDate());
        return ResponseEntity.ok(topItems);
    }
}
