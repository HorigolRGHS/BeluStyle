package com.emc.belustyle.rest;

import com.emc.belustyle.entity.Color;
import com.emc.belustyle.entity.Size;
import com.emc.belustyle.service.ColorService;
import com.emc.belustyle.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
public class ColorRestController {
    private ColorService colorService;
    @Autowired
    public ColorRestController( ColorService colorService ) {
        this.colorService = colorService;
    }

    @GetMapping
    public List<Color> findAll() {
        return colorService.findAll();
    }
}