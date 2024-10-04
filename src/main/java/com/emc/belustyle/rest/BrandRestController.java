package com.emc.belustyle.rest;

import com.emc.belustyle.entity.Brand;
import com.emc.belustyle.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    @Autowired
    private BrandService brandService;

    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{brandId}")
    public ResponseEntity<?> deleteBrand(@PathVariable int brandId) {
        Brand brand = brandService.findById(brandId);
        if (brand != null) {
            brandService.deleteBrand(brandId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @GetMapping("/all")
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.findAll();
        return ResponseEntity.ok(brands);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @GetMapping("/{brandId}")
    public ResponseEntity<Brand> getBrandById(@PathVariable int brandId) {
        Brand brand = brandService.findById(brandId);
        if (brand != null) {
            return ResponseEntity.ok(brand);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Brand createBrand(@RequestBody Brand brand) {
        brand.setBrandId(0);
        return brandService.createBrand(brand);
    }


    @PutMapping("/{id}")
    public Brand updateBrand( @RequestBody Brand updatedBrand) {
            return brandService.updateBrand(updatedBrand);

    }
}
