package com.emc.belustyle.rest;

import com.emc.belustyle.dto.MonthlyRevenueDTO;
import com.emc.belustyle.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsRestController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsRestController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping("/pie/brand")
    public List<Object[]> brandPie() {
      return   statisticsService.getTotalProductsSoldPerBrand();
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping("/revenue/total")
    public Double totalRevenue() {
        return statisticsService.getTotalRevenue();
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping("/revenue/daily")
    public List<Object[]> dailyRevenue() {
        return statisticsService.getDailyRevenue();
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping("/revenue/monthly")
    public List<MonthlyRevenueDTO> monthlyRevenue() {
        return statisticsService.getMonthlyRevenue();
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping("/order-status")
    public List<Object[]> orderStatusStatistics() {
        return statisticsService.getOrderStatusStatistics();
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping("/best-selling-products")
    public List<Object[]> bestSellingProductsStatistics() {
        return statisticsService.getBestSellingProducts();
    }

}
