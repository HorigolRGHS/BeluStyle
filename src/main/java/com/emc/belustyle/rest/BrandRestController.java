package com.emc.belustyle.rest;

import com.emc.belustyle.entity.Brand;
import com.emc.belustyle.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    @Autowired
    private BrandService brandService;


    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.findAll();
    }


    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable Integer id) {
        return brandService.findById(id);
    }


    @PostMapping
    public Brand createBrand(@RequestBody Brand brand) {
        brand.setBrandId(0);
        return brandService.createBrand(brand);
    }


    @PutMapping("/{id}")
    public Brand updateBrand(@PathVariable Integer id, @RequestBody Brand updatedBrand) {
        return brandService.updateBrand(id, updatedBrand);

    }


    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Integer id) {
        brandService.deleteBrand(id);
    }

}
