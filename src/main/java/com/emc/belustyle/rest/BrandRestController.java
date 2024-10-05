package com.emc.belustyle.rest;

import com.emc.belustyle.dto.SearchBrandDTO;
import com.emc.belustyle.entity.Brand;
import com.emc.belustyle.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    @Autowired
    private BrandService brandService;

    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{brandId}")
    public ResponseEntity<?> deleteBrand(@PathVariable int brandId) {
        Brand brand = brandService.findById(brandId);
        if (brand != null) {
            brandService.deleteBrand(brandId);
            return ResponseEntity.ok("Brand with ID " + brandId + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand with ID " + brandId + " not found.");
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
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @GetMapping("/search")
    public ResponseEntity<List<SearchBrandDTO>> getAllBrand() {
        List<SearchBrandDTO> result = brandService.getAllBrands();
        return ResponseEntity.ok(result);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Brand createBrand(@RequestBody Brand brand) {
        brand.setBrandId(0);
        brand.setCreatedAt(new Date());
        brand.setUpdatedAt(new Date());
        return brandService.createBrand(brand);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PutMapping("/{brandId}")
    public Brand updateBrand( @RequestBody Brand updatedBrand) {
            return brandService.updateBrand(updatedBrand);
    }
}
