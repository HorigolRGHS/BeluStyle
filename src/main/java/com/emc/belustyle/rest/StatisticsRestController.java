package com.emc.belustyle.rest;

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


}
