package com.emc.belustyle.service;

import com.emc.belustyle.dto.MonthlyRevenueDTO;
import com.emc.belustyle.repo.BrandRepository;
import com.emc.belustyle.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    private final BrandRepository brandRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public StatisticsService(BrandRepository brandRepository, OrderRepository orderRepository) {
        this.brandRepository = brandRepository;
        this.orderRepository = orderRepository;
    }

    public List<Object[]> getTotalProductsSoldPerBrand() {
        return brandRepository.findTotalProductsSoldPerBrand();
    }

    public Double getTotalRevenue() {
        return orderRepository.findTotalRevenue();
    }

    public List<Object[]> getDailyRevenue() {
        return orderRepository.findDailyRevenue();
    }

    public List<MonthlyRevenueDTO> getMonthlyRevenue() {
        List<Object[]> rawResults = orderRepository.getMonthlyRevenue();
        return rawResults.stream()
                .map(row -> new MonthlyRevenueDTO(
                        ((Integer) row[0]), // year
                        ((Integer) row[1]), // month
                        ((BigDecimal) row[2])   // totalAmount
                ))
                .collect(Collectors.toList());
    }

    public List<Object[]> getOrderStatusStatistics() {
        return orderRepository.findOrderStatusStatistics();
    }

    public List<Object[]> getBestSellingProducts() {
        return orderRepository.findBestSellingProducts();
    }
}
