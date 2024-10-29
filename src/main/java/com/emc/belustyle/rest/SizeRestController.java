package com.emc.belustyle.rest;

import com.emc.belustyle.entity.Size;
import com.emc.belustyle.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeRestController {
    private SizeService sizeService;
    @Autowired
    public SizeRestController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping
    public List<Size> findAll() {
        return sizeService.findAll();
    }
}
