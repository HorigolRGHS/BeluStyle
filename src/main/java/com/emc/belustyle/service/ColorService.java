package com.emc.belustyle.service;

import com.emc.belustyle.entity.Color;
import com.emc.belustyle.repo.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {
    private ColorRepository colorRepository;
    @Autowired
    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public List<Color> findAll() {
        return colorRepository.findAll();
    }
}
