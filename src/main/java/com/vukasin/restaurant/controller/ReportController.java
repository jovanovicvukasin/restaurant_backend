package com.vukasin.restaurant.controller;

import com.vukasin.restaurant.dto.RevenueReportDTO;
import com.vukasin.restaurant.dto.TopSellingItemDTO;
import com.vukasin.restaurant.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/revenue")
    public ResponseEntity<RevenueReportDTO> getRevenueReport(@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate) {
        if (fromDate.isAfter(toDate)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reportService.getRevenueReport(fromDate, toDate));
    }

    @GetMapping("/top-items")
    public ResponseEntity<List<TopSellingItemDTO>> getTopSellingItems(@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate) {

        if (fromDate.isAfter(toDate)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reportService.getTopSellingItems(fromDate, toDate));
    }
}
